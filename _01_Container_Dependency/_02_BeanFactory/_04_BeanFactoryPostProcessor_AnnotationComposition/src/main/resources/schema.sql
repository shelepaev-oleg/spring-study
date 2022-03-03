CREATE TABLE parent (
                         id   UUID      NOT NULL,
                         name VARCHAR(128) NOT NULL,
                         version INTEGER NOT NULL,
                         PRIMARY KEY (id)
);

CREATE TABLE child (
                        id   UUID      NOT NULL,
                        parent_id UUID,
                        name VARCHAR(128) NOT NULL,
                        version INTEGER NOT NULL,
                        PRIMARY KEY (id),
                        CONSTRAINT FK_PARENT FOREIGN KEY (parent_id) REFERENCES parent(id)
);
ALTER TABLE child ALTER parent_id SET NOT NULL;