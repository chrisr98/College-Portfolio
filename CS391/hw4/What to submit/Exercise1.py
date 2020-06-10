# SYSTEM IMPORTS
from typing import Callable, List, Tuple, Type, Sequence
import matplotlib.pyplot as plt
import numpy as np
import os
import sys
import math


    

def part1()->np.random.seed(1):
    # Picking a random number
    x_t:np_ndarray = np.array([np.random.uniform(low=1.5,high =3) for i in range(1)], dtype = float)
    
    max_iter = 1000
    converged = False
    t = 0;
    delta = 1e-6
    print(delta)
    f_of_x: List(float) = [f(x_t)]
    print("Original X: ",x_t)
    
    while not converged and t < max_iter:
        eta: float = 0.001
        x_t_plus_1: np.ndarray = x_t - eta*df_dx(x_t)
        if(np.linalg.norm(x_t_plus_1 - x_t, ord=2) <= delta):
            converged  = True
        t+=1
        x_t = x_t_plus_1
        f_of_x.append(f(x_t))
    print("X value found: ",x_t)
    print("Number of iterations before convergence: ",t)
    plt.plot(np.arange(len(f_of_x)),f_of_x)
    plt.show()

print()
print("Exercise 1 part 1")
print()
# The equation from the HW
def f(x: np.ndarray) -> np.ndarray:
    return (x**4) + (3*(x**3)) + (10*((x-4)**2))


# df(x)/dx
def df_dx(x: np.ndarray) -> float:
    return (4*(x**3)) + (9*(x**2)) + (20*(x-4))  # derivative of ax^b is bax^(b-1)
 
    
# make some data
x: np.ndarray = np.arange(-100, 100+0.01, 0.01)
y: np.ndarray = f(x)

    
part1()



def part2()->np.random.seed(1):
    # Picking a random number
    x_t:np_ndarray = np.array([np.random.uniform(low=i,high =i+2) for i in range(3)], dtype = float)
    
    max_iter = 1000
    converged = False
    t = 0;
    delta = 1e-6
    print(delta)
    f_of_x: List(float) = [f(x_t)]
    print("Original X: ",x_t)
    
    while not converged and t < max_iter:
        eta: float = 0.1
        x_t_plus_1: np.ndarray = x_t - eta*df_dx(x_t)
        if(np.linalg.norm(x_t_plus_1 - x_t, ord=2) <= delta):
            converged  = True
        t+=1
        x_t = x_t_plus_1
        f_of_x.append(f(x_t))
    print("X value found: ",x_t)
    print("Number of iterations before convergence: ",t)
    plt.plot(np.arange(len(f_of_x)),f_of_x)
    plt.show()


print()
print("Exercise 1 part 2")
print()
# The equation from the HW
def f(x: np.ndarray) -> np.ndarray:
    return np.sum((x-(np.arange(x.shape[-1])+1))**2)


# df(x)/dx
def df_dx(x: np.ndarray) -> float:
    return 2*(x-(np.arange(x.shape[-1])+1)) # derivative of ax^2 is 2ax
 
    
# make some data
x: np.ndarray = np.arange(-100, 100+0.01, 0.01)
y: np.ndarray = f(x)
    
part2()

