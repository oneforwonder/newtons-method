;Clojure implementation of Newton's Method
;Christoper Hoffman - Nov 6th, 2011
;Rob Nagle - Nov 7th, 2011

;We'll solve for the roots of this function.
;f(x) = x^2 - 1.5
(defn f ^double [^double x] 
  (- (* x x) 1.5))

;f'(x) = 2x
(defn f' ^double [^double x] 
  (* 2 x))

(def tolerance (double (Math/pow 10.0 -14)))

(defn test-newton [f]
  (time (doall (map f (range 1 100000))))
  nil)

;Newton's method:
;x_n+1 = x_n - f(x_n)/f'(x_n)

;Original implementation by Chris
;With some variable names changed
(defn newtons-method-c [f f' tolerance x n]
    (if (<= (Math/abs (f x)) tolerance)
        n
        (newtons-method-c f f' tolerance (- x (/ (f x) (f' x))) (+ n 1))))

(println "Original")
(test-newton #(newtons-method-c f f' tolerance % 0))

;Using recur
;This essentially has the effect of turning this into a loop
;instead of doing true function calls each time,
;saving time, memory and potentially preventing a stack overflow.
(defn newtons-method-r [f f' tolerance x n]
    (if (<= (Math/abs (f x)) tolerance)
        n
        (recur f f' tolerance (- x (/ (f x) (f' x))) (+ n 1))))

(println "\nRecur")
(test-newton #(newtons-method-r f f' tolerance % 0))

;Using global variables
;Passing the functions around and looking them up as local variables
;when they are actually constant throughout the process introduces 
;overhead (and probably also make it hard for the JIT to optimize).
;The C program uses global variables like this.
(defn newtons-method-g [x n]
    (if (<= (Math/abs (f x)) tolerance)
        n
        (recur (- x (/ (f x) (f' x))) (+ n 1))))

(println "\nGlobal variables")
(test-newton #(newtons-method-g % 0))

;Higher order function
;But the nice thing about having first-class functions is that we can
;pass functions in as arguments and this is a great example of where
;we would want to do so. Newton's method works on all kinds of math
;functions, so it would be sad if our program was fixed to just one.
;Can we do this without a performance penalty?
;The method below is not as fast as global variables, but is faster
;than passing the functions around. 
;This function takes f, f' and the tolerance and generates a specialized
;version of the newton's method function where these values are fixed 
;for the rest of process.
(defn make-newton-solver [f f' tolerance]
  (fn newton-solver [x0]
    (loop [x x0, n 0]
      (if (<= (Math/abs (f x)) tolerance)
          n
          (recur (- x (/ (f x) (f' x))) (+ n 1))))))

(println "\nHigher-order function")
(test-newton (make-newton-solver f f' tolerance))

;Type hints
;Okay, now for the big guns. For this function to run fast, it needs to know
;the types of its varables like the C program does.
(defn abs ^double [^double x]
  (if (< 0 x) x (- x))) 

(defn make-newton-solver-t [f f' ^double tolerance]
  (fn newton-solver ^long [^double x0]
    (loop [x x0, n (long 0)]
      (if (<= (abs (f x)) tolerance)
          n
          (recur (- x (/ (f x) (f' x))) (+ n 1))))))

(println "\nHigher order function and type hints")
(test-newton (make-newton-solver-t f f' tolerance))

;JIT optimizations
;;The hotspot Java just-in-compiler kicks in with optimizations after a 
;function has been run many times. Lets give it plenty of time to warm
;up then time our function.
(println "\nHigher order function, type hints and JIT")
(let [solver (make-newton-solver-t f f' tolerance)]
  (doall (map solver (range 1 (* 1000 100))))
  (test-newton solver))

