ALTER TABLE "user" RENAME TO vote;

ALTER TABLE public.vote RENAME COLUMN name TO comment;
