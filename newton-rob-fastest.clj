;The fastest Clojure implementation of Newton's Method
;Rob Nagle - Nov 7th, 2011

(defn abs ^double [^double x]
  (if (< 0 x) x (- x)))

(defn f ^double [^double x] 
  (- (* x x) 1.5))

(defn f' ^double [^double x] 
  (* 2 x))

(def tolerance (double (Math/pow 10.0 -14)))

(defn test-newton [f]
  (time (doall (map f (range 1 100000))))
  nil)

;Newton's method:
;x_n+1 = x_n - f(x_n)/f'(x_n)
(defn make-newton-solver [f f' ^double tolerance]
  (fn newton-solver ^long [^double x0]
    (loop [x x0, n (long 0)]
      (if (<= (abs (f x)) tolerance)
          n
          (recur (- x (/ (f x) (f' x))) (+ n 1))))))

(println "\nHigher order function, type hints and JIT")
(let [solver (make-newton-solver f f' tolerance)]
  (doall (map solver (range 1 (* 1000 100))))
  (repeatedly 10 #(test-newton solver)))
