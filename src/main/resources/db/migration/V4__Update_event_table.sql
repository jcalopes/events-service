ALTER TABLE EVENTS
ALTER COLUMN ID DROP IDENTITY;

ALTER TABLE EVENTS
ALTER COLUMN ID ADD GENERATED BY DEFAULT AS IDENTITY;