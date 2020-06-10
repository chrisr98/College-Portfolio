# SYSTEM IMPORTS
from abc import ABC, abstractmethod
from sklearn.datasets import load_breast_cancer, load_iris
from typing import Callable, List, Tuple, Type, Sequence, Union
import matplotlib.pyplot as plt
import numpy as np


np.random.seed(12345)


# PYTHON PROJECT IMPORTS


def _split(X: np.ndarray, y: np.ndarray,
           split_percent: float) -> Tuple[Tuple[np.ndarray, np.ndarray],
                                          Tuple[np.ndarray, np.ndarray]]:
    rand_floats: np.ndarray = np.random.uniform(low=0, high=1, size=X.shape[0])
    test_indices: np.ndarray = rand_floats < split_percent
    train_indices: np.ndarray = rand_floats >= split_percent

    return (X[train_indices,:], y[train_indices]),\
           (X[test_indices,:], y[test_indices])


def loadhw4() -> Tuple[Tuple[np.ndarray, np.ndarray],
                       Tuple[np.ndarray, np.ndarray],
                       Tuple[np.ndarray, np.ndarray]]:

    # load data as tuple of X and y
#     X, y = load_breast_cancer(return_X_y=True)
    X, y = load_iris(return_X_y=True)    

    # make the ground truth 2-d (a big column vector)
    y = y.reshape(-1,1)

    # split data into training and test set (10% of data into test set)
    (X_train, y_train), test_set = _split(X, y, 0.1)

    # split remaining training set into training and val set (10% of data into val set)
    train_set, val_set = _split(X_train, y_train, 0.1)

    return train_set, val_set, test_set


# numerical gradient checking...this is how we check whether
# our backpropogation works or not. What we do is compute the
# numerical partial derivative with respect to each learnable
# parameter. A numerical derivative can be computed using:
#       df/dx = (f(x+e) - f(x-e))/(2*e)
# if we set e to be really small, then we can get a good approx
# of the gradient. We can compare the symbolic gradients
# versus the numerical gradients, and hope they are super close
def grad_check(X: np.ndarray, Y_gt: np.ndarray, m: object, ef: object,
               epsilon: float = 1e-5, delta: float = 1e-6) -> None:
    params: List[Parameter] = m.parameters()
    num_grads: List[np.ndarray] = [np.zeros_like(P.val) for P in params]
    sym_grads: List[np.ndarray] = [P.grad for P in params]

    for P, N in zip(params, num_grads):
        for index, v in np.ndenumerate(P.val):
            P.val[index] += epsilon
            N[index] += ef.forward(m.forward(X), Y_gt)

            P.val[index] -= 2*epsilon
            N[index] -= ef.forward(m.forward(X), Y_gt)

            # set param back to normal
            P.val[index] = v
            N[index] /= (2*epsilon)

    ratios: np.ndarray = np.array([np.linalg.norm(SG-NG)/
                                   np.linalg.norm(SG+NG)
                                   for SG, NG in zip(sym_grads, num_grads)], dtype=float)
    if np.sum(ratios > delta) > 0:
        raise RuntimeError("ERROR: failed grad check. delta: [%s], ratios: %s" % (delta, ratios))


def make_minibatches(X: np.ndarray, Y_gt: np.ndarray,
                     minibatch_size: int) -> Tuple[np.ndarray, np.ndarray]:
    if X.shape[0] != Y_gt.shape[0]:
        raise RuntimeError("ERROR: shape mismatch. X and Y_gt have different # of examples. " +
                           "X.shape: %s Y_gt.shape: %s" % (X.shape, Y_gt.shape))

    num_examples: int = X.shape[0]
    if minibatch_size <= 0:
        raise ValueError("ERROR: minibatch_size cannot be <= 0. minibatch_size: %s" % minibatch_size)
    if minibatch_size > num_examples:
        minibatch_size = num_examples
    num_minibatches: int = int(np.ceil(num_examples/minibatch_size))

    if num_minibatches == 1:
        yield X, Y_gt

    batch_mask: np.ndarray = np.random.randint(low=0, high=num_minibatches, size=num_examples)
    for batch_id in range(num_minibatches):
        yield X[batch_mask==batch_id], Y_gt[batch_mask==batch_id]


# our error function. This is given to you in a separate file
# but I figured I would include it here.
class BCE(object):

    @staticmethod
    def forward(Y_hat: np.ndarray, Y_gt: np.ndarray) -> float:
        if Y_hat.shape != Y_gt.shape:
            raise RuntimeError("ERROR: shape mismatch. Y_hat and Y_gt have separate shapes. " +
                               "Y_hat.shape: %s Y_gt.shape: %s" % (Y_hat.shape, Y_gt.shape))
        return (np.sum(-np.log(Y_hat[Y_gt==0])) + np.sum(-np.log(1-Y_hat[Y_gt!=0])))/(Y_hat.shape[0])

    @staticmethod
    def backward(Y_hat: np.ndarray, Y_gt: np.ndarray) -> np.ndarray:
        if Y_hat.shape != Y_gt.shape:
            raise RuntimeError("ERROR: shape mismatch. Y_hat and Y_gt have separate shapes. " +
                               "Y_hat.shape: %s Y_gt.shape: %s" % (Y_hat.shape, Y_gt.shape))
        assert(len(Y_hat.shape) == 1 or (len(Y_hat.shape) == 2 and Y_hat.shape[-1] == 1))
        dE_dYhat = np.zeros(Y_hat.shape)
        dE_dYhat[Y_gt==0] = -1.0/(Y_hat[Y_gt==0])
        dE_dYhat[Y_gt!=0] = 1.0/(1-Y_hat[Y_gt!=0])

        dE_dYhat /= Y_hat.shape[0]
        return dE_dYhat


class MSE(object):
    @staticmethod
    def forward(Y_hat: np.ndarray, Y_gt: np.ndarray) -> float:
        return np.sum((Y_hat - Y_gt)**2) / (2*Y_hat.shape[0])

    @staticmethod
    def backward(Y_hat: np.ndarray, Y_gt: np.ndarray) -> np.ndarray:
        return (Y_hat - Y_gt)/Y_hat.shape[0]


# our sigmoid function. I chose to separate this out because
# I prefer a more modular design
class Sigmoid(object):

    @staticmethod
    def forward(X: np.ndarray) -> np.ndarray:
        return 1/(1+np.exp(-X))

    @staticmethod
    def backward(X: np.ndarray, de_dSigmoid) -> np.ndarray:
        # TODO compute de_dX i.e. partial derivative of sigmoid with respect to inputs X
        dSigmoid_dX: np.ndarray = None
            
        Y_hat: np.ndarray = Sigmoid.forward(X)
        dSigmoid_dX: np.ndarray = Y_hat*(1 - Y_hat)
        
        if dSigmoid_dX is None:
            raise RuntimeError("ERROR: please complete Sigmoid.backward(...)!")

        if dSigmoid_dX.shape != de_dSigmoid.shape:
            raise RuntimeError("ERROR: shape mismatch. Since Sigmoid is an element-wise independent "+
                               "activation f, its gradient will have the same shape as de_dSigmoid.")



        return np.multiply(dSigmoid_dX, de_dSigmoid) # element-wise multiplication


# a class that contains the numpy array for a parameter as well as its gradient
class Parameter(object):
    def __init__(self, V_init: np.ndarray) -> None:
        self.val: np.ndarray = V_init
        self.grad: np.ndarray = None

    def reset(self) -> None:
        self.grad = np.zeros_like(self.val)

    def step(self, D: np.ndarray) -> None:
        self.val += D
        self.reset()


# FINALLY, our logistic regression class (whew!)
class LR(object):
    def __init__(self, num_features: int) -> None:
        self.W: Parameter = Parameter(np.random.randn(num_features, 1))
        self.b: Parameter = Parameter(np.random.randn(1,1))
        self.af = Sigmoid

    # used by the grad_check function
    def parameters(self, as_vector: bool = False) -> Union[np.ndarray, List[Parameter]]:
        if as_vector:
            return np.vstack([self.W.val.reshape(-1,1), self.b.val.reshape(-1,1)])
        return [self.W, self.b]

    def forward(self, X: np.ndarray) -> np.ndarray:
        return Sigmoid.forward(np.dot(X, self.W.val) + self.b.val)

    def classify(self, X: np.ndarray) -> np.ndarray:
        # need Pr(c_0|x) = 1 maps to class 0
        # and  Pr(c_0|x) = 0 maps to class 1
        return np.abs(1-np.round(self.forward(X)))

    def backward(self, X: np.ndarray, de_dYhat: np.ndarray) -> None:
        de_dW: np.ndarray = np.zeros_like(self.W.val)
        de_db: np.ndarray = np.zeros_like(self.b.val)
             
        # TODO: compute de/dW, de/db
            
        Z: np.ndarray = np.dot(X, self.W.val) + self.b.val

        dE_dZ: np.ndarray = self.af.backward(Z, de_dYhat)

        de_db = np.sum(dE_dZ, axis=0, keepdims=True)
        de_dW = np.dot(X.T, dE_dZ)
        
        self.W.grad += de_dW
        self.b.grad += de_db


        

    def SGD(self, X: np.ndarray, Y_gt: np.ndarray, eta: float,
            minibatch_size: int = None,
            check_grads: bool = False) -> None:
        # perform one update of SGD....a full pass through the data
        # although if minibatch_size is None, we will just perform
        # vanilla gradient descent
        if minibatch_size is None:
            minibatch_size = X.shape[0]

        # reset the gradients for the new pass through the data
        for P in self.parameters():
            P.reset()

        for X_batch, Y_batch in make_minibatches(X, Y_gt, minibatch_size):
            self.backward(X_batch, BCE.backward(self.forward(X_batch), Y_batch))

            if check_grads:
                grad_check(X_batch, Y_batch, self, BCE)

            # this applies the actual gradient descent update
            for P in self.parameters():
                P.step(-eta*P.grad)

    def train(self, X: np.ndarray, Y_gt: np.ndarray, eta: float,
              minibatch_size: int = None,
              check_grads: bool = False,
              max_epochs: int = 1e6,
              val_func: Callable[["LR"], None] = None) -> None:

        epsilon: float = 1e-9
        t: int = 0
        err: float = np.inf

        # list our all of our parameters as one big vector
        params_t: np.ndarray = self.parameters(as_vector=True).copy()

        while err > epsilon and t < max_epochs:
            self.SGD(X, Y_gt, eta, minibatch_size=minibatch_size, check_grads=check_grads)
            if val_func is not None:
                val_func(self)

            params_t_plus_1: np.ndarray = self.parameters(as_vector=True).copy()
            err = np.linalg.norm(params_t_plus_1-params_t, ord=2)
            t += 1
            params_t = params_t_plus_1
        


def test_model_xor() -> None:
    print("testing model on XOR data")

    # test our data on XOR
    X: np.ndarray = np.array([[0,0],
                              [0,1],
                              [1,0],
                              [1,1]], dtype=float)
    Y_gt: np.ndarray = np.array([[0,1,1,0]], dtype=float).T

    errors: List[float] = list()
    def track_training_error(m: LR) -> None:
        errors.append(BCE.forward(m.forward(X), Y_gt))

    m = LR(X.shape[-1])
    m.train(X, Y_gt, eta=0.1, check_grads=True, max_epochs=750, val_func=track_training_error)
    plt.plot(np.arange(len(errors)), errors)
    plt.ylabel("training error")
    plt.xlabel("epoch")
    plt.show()

    training_acc: float = np.sum(m.classify(X).reshape(-1) == Y_gt.reshape(-1))/Y_gt.shape[0]
    print("accuracy on training data: %s" % training_acc)
    print("done! Test PASSED")


def test_model_iris() -> None:
    print("testing model on Iris data")
    X, Y_gt = load_iris(return_X_y=True)
    Y_gt = Y_gt.reshape(-1, 1)

    zero_cls_mask: np.ndarray = Y_gt == 0
    one_cls_mask: np.ndarray = Y_gt != 0
    Y_gt[zero_cls_mask] = 1
    Y_gt[one_cls_mask] = 0

    errors: List[float] = list()
    def track_training_error(m: LR) -> None:
        errors.append(BCE.forward(m.forward(X), Y_gt))

    m = LR(X.shape[-1])
    m.train(X, Y_gt, eta=0.01, check_grads=True, max_epochs=1e4, val_func=track_training_error)
    plt.plot(np.arange(len(errors)), errors)
    plt.ylabel("training error")
    plt.xlabel("epoch")
    plt.show()

    training_acc: float = np.sum(m.classify(X).reshape(-1) == Y_gt.reshape(-1))/Y_gt.shape[0]
    print("accuracy on training data: %s" % training_acc)
    print("done! Test PASSED")


def main() -> None:
#     try:
#         test_model_xor()
#         print()
#         test_model_iris()
#         print("ALL TESTS PASSED. Your model is working.")
#         print()
#     except:
#         print("TESTING FAILED. There are some problems with your model.")
#         raise

    # step 1) load in the data
    (X_train, Y_train), (X_val, Y_val), (X_test, Y_test) = loadhw4()
    X, Y_gt = load_iris(return_X_y=True)
    Y_gt = Y_gt.reshape(-1, 1)
    
    unique_classes, counts = np.unique(Y_gt,  return_counts=True)
    print(unique_classes, counts)

    # iris dataset has 3 labels and here we convert it to binary
    zero_mask: np.ndarray = Y_gt == 0
    nonzero_mask: np.ndarray = Y_gt != 0

    Y_gt[zero_mask] = 0
    Y_gt[nonzero_mask] = 1

    unique_classes, counts = np.unique(Y_gt, return_counts=True)
    print(unique_classes, counts)

    # step 2) convert ground truth data from the class label ([0,1]) into Pr(c_0 | x)
    def convert_cls_to_pr(Y_gt_classes: np.ndarray) -> np.ndarray:
        Y_gt_pr: np.ndarray = np.zeros_like(Y_gt_classes)
        zero_cls_mask: np.ndarray = Y_gt_classes == 0
        one_cls_mask: np.ndarray = Y_gt_classes != 0

        # if the class is 0, then Pr(c_0|x) == 1 and vice versa
        Y_gt_pr[zero_cls_mask] = 1
        Y_gt_pr[one_cls_mask] = 0
        return Y_gt_pr

    Y_train = convert_cls_to_pr(Y_train)
    Y_val = convert_cls_to_pr(Y_val)
    Y_test = convert_cls_to_pr(Y_test)

    # step 3) train your model on the data
    def convert_pr_to_cls(Y_gt_pr: np.ndarray) -> np.ndarray:
        return np.abs(1-np.round(Y_gt_pr))

    # feel free to use this function to evaluate your model on
    # the validation data. This function measures accuracy, but
    # this may not be the best metric (check data balance!!!!)
    metrics: List[float] = list()
    params_per_epoch: List[Tuple[np.ndarray]] = list()
    def eval_acc(m: LR) -> None:
        Y_hat_cls: np.ndarray = m.classify(X_val)
        Y_gt_cls: np.ndarray = convert_pr_to_cls(Y_val)

        # accuracy
        acc:float = np.sum(Y_hat_cls == Y_gt_cls) / Y_gt_cls.shape[0]
        metrics.append(acc)
        params_per_epoch.append(tuple(P.val.copy() for P in m.parameters()))
        
    def eval_pre(m: LR) -> None:
        Y_hat_cls: np.ndarray = m.classify(X_val)
        Y_gt_cls: np.ndarray = convert_pr_to_cls(Y_val)
        print(Y_hat_cls)
        print(Y_gt_cls)         
        total_length = Y_gt_cls.shape[0]
        # precision
        TP = FP = TN = FN = correctPredictions = 0
        for x in range(total_length):
            tempTrue = Y_gt_cls[x]
            tempPredict = Y_hat_cls[x]
            if tempPredict == tempTrue:
                correctPredictions = correctPredictions+1
                if tempPredict == 1:
                    TP = TP+1
                else:
                    TN = TN+1
            else:
                if tempPredict ==1:
                    FP = FP+1
                else:
                    FN = FN+1
    
        print("TN: ",TN)
        print("FN: ",FN)
        print("TP: ",TP)
        print("FP: ",FP)

        try:
            pre:float =  TP / (TP+FP) #Precision = TruePositives / (TruePositives + FalsePositives)
        except ZeroDivisionError:
            pre = 0
        metrics.append(pre)
        params_per_epoch.append(tuple(P.val.copy() for P in m.parameters()))

    # TODO: train and evaluate your model on the val data after every epoch   
    m = LR(X_train.shape[-1])
    m.train(X_train, Y_train, eta=1e-6, check_grads=True, max_epochs=1000, val_func=eval_pre)
    # step 4) look at your metric results! Use the metrics array
    #         to pick the best model
    print
    plt.plot(np.arange(len(metrics)), metrics)
    plt.xlabel("epoch")
    plt.ylabel("metric")
    plt.show()

    # step 5) test your model on the testing data
    Y_hat_cls: np.ndarray = m.classify(X_test)
    Y_gt_cls: np.ndarray = convert_pr_to_cls(Y_test)

    # step 6) how well did you do? Use some metric


if __name__ == "__main__":
    main()


