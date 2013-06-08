
(let ((m (make-hash-table :test 'equal)))
  (setf (gethash 'c m) 0)
  (setf (gethash 'b m) 1)
  (setf (gethash 'a m) 2)
  (setf (gethash 'd m) 3)
  (format t "~%MAP=~%")
  (maphash (lambda (k v) (format t "~a => ~a~%" k v))
	   m))
