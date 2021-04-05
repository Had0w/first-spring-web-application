ALTER TABLE products ADD COLUMN views integer DEFAULT 0;

UPDATE products SET views = 0;