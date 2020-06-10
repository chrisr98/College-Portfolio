# SYSTEM IMPORTS
import matplotlib.pyplot as plt
import numpy as np


# PYTHON PROJECT IMPORTS


def pdf(X: np.ndarray, mu: float, var: float) -> np.ndarray:
    # pr(x | N(mu, var))
    return 1/np.sqrt(2*np.pi*var)*np.exp(-((X-mu)**2)/(2*var))


class GMM(object):
    def __init__(self, k: int) -> None:
        self.k: int = int(k)

        # really can randomly choose means...see comment in GMM::fit_one_iter
        self.mus: np.ndarray = None
        self.vars: np.ndarray = 10*np.random.rand(self.k)
        self.priors: np.ndarray = np.ones(self.k) / k

    def log_likelihood(self, X: np.ndarray) -> float:
        likelihoods: np.ndarray = np.hstack([pdf(X, mu, var).reshape(-1, 1)
                                             for mu,var in zip(self.mus, self.vars)])
        likelihoods *= self.priors.reshape(1,-1)
        return np.sum(np.log(np.sum(likelihoods, axis=1)))

    def fit_one_iter(self, X: np.ndarray) -> None:

        # this is just a choice we made so that we can always see our gaussians
        # in the plot. Really you can pick randomly, but by guaranteeing that
        # the means of our gaussians are in the data, we guarantee that we can
        # see them in our plots.        
        if self.mus is None:
            self.mus = np.random.choice(X.reshape(-1), self.k)

        # E step
        # [num_examples, k]
        likelihoods: np.ndarray = np.hstack([pdf(X, mu, var).reshape(-1, 1)
                                             for mu,var in zip(self.mus, self.vars)])
        posteriors: np.ndarray = likelihoods * self.priors.reshape(1, -1)
        posteriors /= np.sum(posteriors, axis=1, keepdims=True)

        # M step
        self.mus = np.sum(posteriors * X.reshape(-1,1), axis=0) / np.sum(posteriors, axis=0)
        self.vars = np.sum(posteriors * ((X.reshape(-1,1) - self.mus.reshape(1,-1)) ** 2), axis=0) / np.sum(posteriors, axis=0)
        self.priors = np.mean(posteriors, axis=0)

    def fit(self, X: np.ndarray, num_iter: int = 10000, epsilon: float = 1e-8) -> "GMM":
        delta: float = np.inf
        current_iter: int = 0
        prev_log_likelihood: float = self.log_likelihood(X)

        while current_iter < num_iter and delta > epsilon:
            self.fit_one_iter(X)

            current_log_likelihood: float = self.log_likelihood(X)
            delta = abs(current_log_likelihood - prev_log_likelihood) / abs(current_log_likelihood)
            prev_log_likelihood = current_log_likelihood
            current_iter += 1

        return self


if __name__ == "__main__":
    num_samples: int = 100
    num_gaussians: int = 3

    mus: np.ndarray = np.array([-4, 4, 0], dtype=float)
    real_vars: np.ndarray = np.array([1.2, 0.8, 2], dtype=float)

    X: np.ndarray = np.hstack([np.random.normal(loc=mu, scale=var, size=num_samples)
                               for mu, var in zip(mus, real_vars)])

    # plot this data
    x_pts: np.ndarray = np.arange(np.min(X), np.max(X), 0.01)

    # m = GMM(3).fit(X)

    m = GMM(3)
    lls = list()
    for i in range(100):
        m.fit_one_iter(X)
        lls.append(m.log_likelihood(X))
        # if i % 10 == 0:
        #     for mu, var in zip(mus, real_vars):
        #         plt.plot(x_pts, pdf(x_pts, mu, var), color="b")
        #     for mu, var in zip(m.mus, m.vars):
        #         plt.plot(x_pts, pdf(x_pts, mu, var), color="r")
        #     plt.show()
    plt.plot(np.arange(len(lls)), lls)
    plt.show()

