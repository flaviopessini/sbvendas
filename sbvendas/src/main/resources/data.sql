CREATE TABLE clientes (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL
);

CREATE TABLE produtos (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(200) NOT NULL,
  descricao VARCHAR(255),
  valor_unit NUMERIC(10, 2)
);

CREATE TABLE pedidos (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  cliente_id INTEGER REFERENCES clientes(id),
  data TIMESTAMP,
  total NUMERIC(10, 2)
);

CREATE TABLE item_pedido (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  pedido_id INTEGER REFERENCES pedidos(id),
  produto_id INTEGER REFERENCES produtos(id),
  quantidade INTEGER,
  valor_unit NUMERIC(10, 2)
);