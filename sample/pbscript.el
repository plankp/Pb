;; pb-script-mode
;; Maintained by Plankp T.

(setq pb-highlights
      '(("@." . font-lock-variable-name-face)
	("%%.*?%%" . font-lock-constant-face)
	(".=" . font-lock-function-name-face)
	("# .*" . font-lock-comment-face)))

(define-derived-mode pb-script-mode fundamental-mode
  (setq font-lock-defaults '(pb-highlights))
  (setq mode-name "pb script"))

(provide 'pb-script-mode)
