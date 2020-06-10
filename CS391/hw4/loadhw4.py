# SYSTEM IMPORTS
from typing import Tuple
import numpy as np
from sklearn.datasets import load_breast_cancer


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
    X, y = load_breast_cancer(return_X_y=True)

    # split data into training and test set (10% of data into test set)
    (X_train, y_train), test_set = _split(X, y, 0.1)

    # split remaining training set into training and val set (10% of data into val set)
    train_set, val_set = _split(X_train, y_train, 0.1)

    return train_set, val_set, test_set


if __name__ == "__main__":
    (X_train, y_train), (X_val, y_val), (X_test, y_test) = loadhw4()
    print(X_train.shape, y_train.shape)
    print(X_val.shape, y_val.shape)
    print(X_test.shape, y_test.shape)

