INSERT INTO addresses VALUES (DEFAULT, "stad", "achternaam", "voornaam", "tussennaam", "huisnrtvg", "1", "straat", "postcode");
INSERT INTO users VALUES (DEFAULT, "EMPLOYEE", "werknemer@domein.nl", "T", "p7iSLIezNrbmpTNGeaUXuw==$AGXMdphftqc=", "ADMIN", "1");
INSERT INTO roles VALUES (DEFAULT, "werknemer@domein.nl", "EMPLOYEE")
INSERT INTO users VALUES (DEFAULT, "CUSTOMER", "klant@domein.nl", "T", "p7iSLIezNrbmpTNGeaUXuw==$AGXMdphftqc=", "USER", "1");
INSERT INTO roles VALUES (DEFAULT, "klant@domein.nl", "CUSTOMER")
INSERT INTO carts VALUES (DEFAULT, "10.00", "1", "1");
INSERT INTO carts VALUES (DEFAULT, "10.00", "1", "2");
INSERT INTO products VALUES (DEFAULT, "Danablue", "BLUE", "info", "Danablue", "2.50", "500");
INSERT INTO products VALUES (DEFAULT, "President", "GOAT", "info", "Geitenkaas", "2.50", "500");
INSERT INTO products VALUES (DEFAULT, "Paturain", "CREAM", "info", "Kruidenroomkaas", "2.50", "500");
INSERT INTO cart_suborders VALUES (DEFAULT, "1", "2.50", "1", "1");
INSERT INTO cart_suborders VALUES (DEFAULT, "1", "2.50", "2", "1");
INSERT INTO cart_suborders VALUES (DEFAULT, "1", "2.50", "3", "1");
INSERT INTO cart_suborders VALUES (DEFAULT, "1", "2.50", "1", "2");
INSERT INTO cart_suborders VALUES (DEFAULT, "1", "2.50", "2", "2");
INSERT INTO cart_suborders VALUES (DEFAULT, "1", "2.50", "3", "2");