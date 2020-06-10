'''
Created on Oct 4, 2019

@author: Chris-Emio Raymond, Zhan Hao Xu, Ivan Mata

'''

import numpy as np
import os, sys
import csv 
from numpy import vsplit
import random
import matplotlib.pyplot as plt






class NeuralNetwork:
    def __init__(self):
        self.inputLayerSize = 2
        self.outputLayerSize = 1
        self.NNodes = 3
        
        #randomly generated weights
        self.W1 = np.random.randn(self.inputLayerSize, self.NNodes)
        self.W2 = np.random.randn(self.NNodes, self.outputLayerSize)
        
        
        #regularization lambda
        self.Lambda = 0
        
    def fit(self, X, Y, learningRate, epochs, regLambda):
        """
        This function is used to train the model.
        Parameters
        ----------
        X : numpy matrix
            The matrix containing sample features for training.
        Y : numpy array
            The array containing sample labels for training.
        Returns
        -------
        None
        """
        #Learns model parameters to fit the data.
        
        #first forward, then backprop, then find gradient
        for singleEpoch in range(X.shape[0]):
            forwardTest = self.forward(X)
            
            
            
        
        
         

    def predict(self, X):
        """
        Predicts the labels for each sample in X.
        Parameters
        X : numpy matrix
            The matrix containing sample features for testing.
        Returns
        -------
        YPredict : numpy array
            The predictions of X.
        ----------
        """

        z = np.dot(X, self.W1) + 1
        exp = np.exp(z)
        softmax_scores = exp/ (exp + 1) 
        YPredict = np.argmax(softmax_scores, axis = 1)
        return YPredict
        
        

    def forward(self, X):
        # Perform matrix multiplication and activation twice (one for each layer).
        # (hint: add a bias term before multiplication)
        self.z1 = np.dot(X, self.W1) + 1
        self.sigmoidZ1 = self.sigmoid(self.z1)
        self.z2 = np.dot(self.sigmoidZ1, self.W2)
        sigmoidZ2 = self.sigmoid(self.z2)
        
        return sigmoidZ2
        
        
        
    
    def sigmoid(self, z):
        #
        return 1/(1+np.exp(-z))    
    
    def backpropagate(self, X, Y, forwardTest):
        # Compute loss / cost using the getCost function.

        # Compute gradient for each layer.

        # Update weight matrices.
        pass
        
    def getCost(self, YTrue, YPredict):
        # Compute loss / cost in terms of crossentropy.
        # (hint: your regularization term should appear here)

#         sum(YPredict-YTrue)**2
        total_Num = YPredict.shape[0]
        result = 0
        
        for x in range(total_Num):
            temp = YPredict[x]-YTrue[x]
            temp = temp ** 2
            result = temp + result
                        
        result = result/total_Num
        
        return result
        



def getData(dataDir):
    '''
    Returns
    -------
    X : numpy matrix
        Input data samples.
    Y : numpy array
        Input data labels.
    '''
    # TO-DO for this part:
    # Use your preferred method to read the csv files.
    # Write your codes here:
    files = os.listdir(dataDir)
    X=[]  
    Y=[]  
    for singleFile in files:
        if (singleFile == "LinearX.csv" or singleFile == "NonlinearX.csv"):     
            with open(singleFile) as csvfile:
                readData = csv.reader(csvfile, delimiter=',')
                tempList = list(readData)
                X = np.array(tempList).astype("float")
#                 X = np.matrix(tempList)
        elif (singleFile == "LinearY.csv" or singleFile == "NonlinearY.csv"):
            with open(singleFile) as csvfile:
                readData = csv.reader(csvfile, delimiter=',')
                tempList = list(readData)
                Y = np.array(tempList).astype("float")
#                 Y = np.matrix(tempList)

    # Hint: use print(X.shape) to check if your results are valid.
    return X, Y

def splitData(X, Y, K = 5):
    '''
    Returns
    -------
    result : List[[train, test]]
        "train" is a list of indices corresponding to the training samples in the data.
        "test" is a list of indices corresponding to the testing samples in the data.
        For example, if the first list in the result is [[0, 1, 2, 3], [4]], then the 4th
        sample in the data is used for testing while the 0th, 1st, 2nd, and 3rd samples
        are for training.
    '''
    temp = vsplit(X,K)
    result = []
    a = []
    d = []
    tempIndices = 0
    
    for x in temp: 
        a.append(tempIndices)
        tempIndices += 1
    for testIndex in a:
        # create a list of all indices except for the test index
        train = [x for j,x in enumerate(a) if j!=testIndex] 
        random.shuffle(train)
        test = [testIndex]
        d.append(train)
         
        d.append(test)
        result.append(d)
        d = []
    # Make sure you shuffle each train list.
    return result


def plotDecisionBoundary(model, X, Y): 
    """
    Plot the decision boundary given by model.
    Parameters
    ----------
    model : model, whose parameters are used to plot the decision boundary.
    X : input data
    Y : input labels
    """
    x1_array, x2_array = np.meshgrid(np.arange(-4, 4, 0.01), np.arange(-4, 4, 0.01))
    grid_coordinates = np.c_[x1_array.ravel(), x2_array.ravel()]
    Z = model.predict(grid_coordinates)
    Z = Z.reshape(x1_array.shape)
    plt.contourf(x1_array, x2_array, Z, cmap=plt.cm.bwr)
    plt.scatter(X[:, 0], X[:, 1], c=Y, cmap=plt.cm.bwr)
    plt.show()


def train(XTrain, YTrain):
    """
    This function is used for the training phase.
    Parameters
    ----------
    XTrain : numpy matrix
        The matrix containing samples features (not indices) for training.
    YTrain : numpy array
        The array containing labels for training.
    args : List
        The list of parameters to set up the NN model.
    Returns
    -------
    NN : NeuralNetwork object
        This should be the trained NN object.
    """
    
    # 1. Initializes a network object with given args.
    matrixForTesting = NeuralNetwork()
    
    # 2. Train the model with the function "fit".
    # (hint: use the plotDecisionBoundary function to visualize after training)
    matrixForTesting.fit(XTrain, YTrain, 0.0001, 2000, 10)
    
    #Change out dataDir to where the data is located (i.e. file location)
    getLists = getData("/Users/chrisr98/Desktop/College/CS 440/Workspace/440_P1/dataset1")
    x = getLists[0]
    y = getLists[1]
    plotDecisionBoundary(matrixForTesting, x, y)
    
    # 3. Return the model.
    return matrixForTesting
    

def test(XTest, model):
    """
    This function is used for the testing phase.
    Parameters
    ----------
    XTest : numpy matrix
        The matrix containing samples features (not indices) for testing.
    model : NeuralNetwork object
        This should be a trained NN model.
    Returns
    -------
    YPredict : numpy array
        The predictions of X.
    """
    print ("here")
    res = model.predict(XTest)
    return res

def getConfusionMatrix(YTrue, YPredict): 
    """
    Computes the confusion matrix.
    Parameters
    ----------
    YTrue : numpy array
        This array contains the ground truth.
    YPredict : numpy array
        This array contains the predictions.
    Returns
    CM : numpy matrix
        The confusion matrix.
    """
    total_length = YTrue.shape[0]
    correctPredictions = 0
    TP = 0
    TN = 0
    FP = 0
    FN = 0
    for x in range(total_length):
        tempTrue = YTrue[x]
        tempPredict = YPredict[x]
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
    res = [[TP,FN],[FP,TN]]
    return res
    
def getPerformanceScores(YTrue, YPredict):
    """
    Computes the accuracy, precision, recall, f1 score.
    Parameters
    ----------
    YTrue : numpy array
        This array contains the ground truth.
    YPredict : numpy array
        This array contains the predictions.
    Returns
    {"CM" : numpy matrix,
    "accuracy" : float,
    "precision" : float,
    "recall" : float,
    "f1" : float}
        This should be a dictionary.
    """
    cm = getConfusionMatrix(YTrue, YPredict)
    TP = cm[0][0]
    FN = cm[0][1]
    FP = cm[1][0]
    TN = cm[1][1]
    correctVal = TP+TN
    totalVal = YTrue.shape[0]
    a = correctVal/totalVal
    p = TP/(TP+FP)
    r =  TP/(TP+FN)
    f1 = (2*p*r)/(p+r)
    
    print("CM :") 
    print(cm)
    print("accuracy :")(a)
    print("precision :")(p)
    print("recall :")(r)
    print("f1 :"),(f1)
    
    


testGetData = getData("/Users/chrisr98/Desktop/College/CS 440/Workspace/440_P1/dataset1")
# print testGetData

testsplitData = splitData(testGetData[0], testGetData[1], 5)
# print testsplitData


Xmatrix = testsplitData[0][0][0]

begin = Xmatrix*100
end = begin+100

bar = testGetData[1][begin:end]
foo = testGetData[0][begin:end]

testMatrix = testGetData[0][0:100]

testTrain = train(foo, bar)


a = test(testMatrix, testTrain)
b = testGetData[1][0:1]

cmData = getPerformanceScores(b, a)

print cmData


