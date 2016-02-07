(define-fun add((x!1 Int)(x!2 Int)(x!3 Int)(x!4 Int)) Bool
	(= x!1 (+ x!2 (+ x!3 x!4)))
)
(define-fun smaller((x!1 Int)(x!2 Int)) Bool
	(< x!1 x!2) 
)
