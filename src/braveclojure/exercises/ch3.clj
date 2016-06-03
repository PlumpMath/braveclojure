(ns braveclojure.exercises.ch3)

;; http://www.braveclojure.com/do-things/

;; These exercises are meant to be a fun way to test your Clojure knowledge and
;; to learn more Clojure functions. The first three can be completed using only
;; the information presented in this chapter, but the last three will require
;; you to use functions that haven’t been covered so far. Tackle the last three
;; if you’re really itching to write more code and explore Clojure’s standard
;; library. If you find the exercises too difficult, revisit them after reading
;; Chapters 4 and 5—you’ll find them much easier.

;; ------------------------------------------------------------
;; 1. Use the str, vector, list, hash-map, and hash-set functions.
(str "hello, " "world")                 ; => hello, world
(list 1 2)                              ; => (1 2)
(hash-map :name "John")                 ; => {:name "John"}
(hash-set 1 1 2 3)                      ; => #{1 2 3}

;; ------------------------------------------------------------
;; 2. Write a function that takes a number and adds 100 to it.
(defn add-100 [val]
  (+ 100 val))

(add-100 1)                             ; => 101

;; ------------------------------------------------------------
;; 3. Write a function, dec-maker, that works exactly like the function
;; inc-maker except with subtraction:
;; (def dec9 (dec-maker 9))
;; (dec9 10)
;; ; => 1

(defn dec-maker [num]
  #(- % num))

(def dec9 (dec-maker 9))
(dec9 10)                               ; => 1

;; ------------------------------------------------------------
;; 4. Write a function, mapset, that works like map except the return value is a set:
;; (mapset inc [1 1 2 2])
;; ; => #{2 3}

;; 1st step:
(set (map inc [1 1 2 2]))

;; 2nd step:
(defn mapset [f coll]
  (set (map f coll)))

(mapset inc [1 1 2 2])                  ; => #{2 3}

;; ------------------------------------------------------------
;; 5. Create a function that’s similar to symmetrize-body-parts except that it
;; has to work with weird space aliens with radial symmetry. Instead of two
;; eyes, arms, legs, and so on, they have five.

(def body-part [{:name "head"   :size 10}
                {:name "eyes-1" :size 1}
                {:name "arms-1" :size 1}
                {:name "legs-1" :size 1}]) ; <= we want ths align's body-part has one head, but has five eyes, arms, legs ...etc.


(declare make-parts)                    ; <= we declare make-parts here, and defined it later
(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (sort-by :name (set (make-parts part 5)))))
          [] asym-body-parts))

;; ------------------------------------------------------------
;; 6. Create a function that generalizes symmetrize-body-parts and the function
;; you created in Exercise 5. The new function should take a collection of body
;; parts and the number of matching body parts to add. If you’re completely new
;; to Lisp languages and functional programming, it probably won’t be obvious
;; how to do this. If you get stuck, just move on to the next chapter and
;; revisit the problem later.

(defn matching-part
  [part i]
  {:name (clojure.string/replace (:name part) #"-1$" (str "-" i))
   :size (:size part)})

(defn make-parts [part count]
  (loop [i 1
         result []]
    (if (> i count)
      result
      (recur (inc i)
             (conj result (matching-part part i))))))

(symmetrize-body-parts body-part)
;; =>
;; [{:name "head", :size 10}
;;  {:name "eyes-1", :size 1}
;;  {:name "eyes-2", :size 1}
;;  {:name "eyes-3", :size 1}
;;  {:name "eyes-4", :size 1}
;;  {:name "eyes-5", :size 1}
;;  {:name "arms-1", :size 1}
;;  {:name "arms-2", :size 1}
;;  {:name "arms-3", :size 1}
;;  {:name "arms-4", :size 1}
;;  {:name "arms-5", :size 1}
;;  {:name "legs-1", :size 1}
;;  {:name "legs-2", :size 1}
;;  {:name "legs-3", :size 1}
;;  {:name "legs-4", :size 1}
;;  {:name "legs-5", :size 1}]
