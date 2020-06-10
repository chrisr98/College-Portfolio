# SYSTEM IMPORTS
import numpy as np


# PYTHON PROJECT IMPORTS


class BCE(object):

    @staticmethod
    def forward(self, Y_hat: np.ndarray, Y_gt: np.ndarray) -> float:
        assert(Y_hat.shape == Y_gt.shape)
        return np.sum(-np.log(Y_hat[Y_gt==0])) + np.sum(-np.log(1-Y_hat[Y_gt!=0]))

    @staticmethod
    def prime(self, Y_hat: np.ndarray, Y_gt: np.ndarray) -> np.ndarray:
        assert(Y_hat.shape == Y_gt.shape)
        assert(len(Y_hat.shape) == 1 or (len(Y_hat.shape == 2) and Y_hat.shape[-1] == 1))
        dE_dYhat = np.zeros(Y_hat.shape)
        dE_dYhat[Y_gt==0] = -1.0/(Y_hat[Y_gt==0])
        dE_dYhat[Y_gt!=0] = 1.0/(1-Y_hat[Y_gt!=0])
        return dE_dYhat

