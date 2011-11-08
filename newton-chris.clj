;Clojure implimentation of NewtonsMethod
;Christoper Hoffman - Nov 6th, 2011

(ns user (:use clojure.contrib.math))

;function to find the root of f(x) = x^2-1.5
(defn f [x] (- (expt x 2) 1.5))

;f'(x)
(defn fp [x] (* 2 x))

;solution tolerance
(def tol (expt 10.0 -14))

;Newtons method implimentation:
;x0+1 = x0 - f(x0)/f'(x0)
(defn newtonsMethod [f fp tol x0 iter]
    (if (<= (abs (f x0)) tol)
        iter
        (newtonsMethod f fp tol (- x0 (/ (f x0) (fp x0))) (+ iter 1))))

;known roots exist at +-sqrt(3/2)
(def l (time (doall(map #(newtonsMethod f fp tol % 0) (range 1 1000)))))
