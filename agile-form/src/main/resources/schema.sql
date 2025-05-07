-- Form Template Table
CREATE TABLE form_template (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Sheet Template Table
CREATE TABLE sheet_template (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Cell Template Table
CREATE TABLE cell_template (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Form-Sheet Relationship Table (Many-to-Many with quantity)
CREATE TABLE form_sheet_rel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_template_name VARCHAR(255) NOT NULL,
    sheet_template_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    position INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (form_template_name) REFERENCES form_template(name) ON DELETE CASCADE,
    FOREIGN KEY (sheet_template_name) REFERENCES sheet_template(name) ON DELETE CASCADE,
    UNIQUE KEY (form_template_name, sheet_template_name, position)
);

-- Sheet-Cell Relationship Table (Many-to-Many with quantity)
CREATE TABLE sheet_cell_rel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sheet_template_name VARCHAR(255) NOT NULL,
    cell_template_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    position INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (sheet_template_name) REFERENCES sheet_template(name) ON DELETE CASCADE,
    FOREIGN KEY (cell_template_name) REFERENCES cell_template(name) ON DELETE CASCADE,
    UNIQUE KEY (sheet_template_name, cell_template_name, position)
);

-- Form Data Table
CREATE TABLE form_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_template_name VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (form_template_name) REFERENCES form_template(name) ON DELETE CASCADE,
    UNIQUE KEY (form_template_name, name)
);

-- Sheet Data Table
CREATE TABLE sheet_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_data_id INT NOT NULL,
    sheet_template_name VARCHAR(255) NOT NULL,
    position INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (form_data_id) REFERENCES form_data(id) ON DELETE CASCADE,
    FOREIGN KEY (sheet_template_name) REFERENCES sheet_template(name) ON DELETE CASCADE,
    UNIQUE KEY (form_data_id, sheet_template_name, position)
);

-- Cell Data Table
CREATE TABLE cell_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sheet_data_id INT NOT NULL,
    cell_template_name VARCHAR(255) NOT NULL,
    position INT NOT NULL,
    data TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (sheet_data_id) REFERENCES sheet_data(id) ON DELETE CASCADE,
    FOREIGN KEY (cell_template_name) REFERENCES cell_template(name) ON DELETE CASCADE,
    UNIQUE KEY (sheet_data_id, cell_template_name, position)
);