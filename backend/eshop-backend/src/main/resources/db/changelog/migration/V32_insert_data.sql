-- =========================================================
-- CATEGORIES
-- =========================================================

INSERT INTO categories (
    category_name,
    category_description,
    is_active,
    created_by,
    updated_by
)
VALUES
(
    'Electronics',
    'Electronic devices and accessories',
    TRUE,
    1,
    1
),
(
    'Computers',
    'Desktop computers and laptops',
    TRUE,
    1,
    1
),
(
    'Smartphones',
    'Mobile phones and accessories',
    TRUE,
    1,
    1
),
(
    'Gaming',
    'Gaming consoles and peripherals',
    TRUE,
    1,
    1
),
(
    'Office',
    'Office equipment and accessories',
    TRUE,
    1,
    1
),
(
    'Home Appliances',
    'Home and kitchen appliances',
    TRUE,
    1,
    1
),
(
    'Audio',
    'Speakers and headphones',
    TRUE,
    1,
    1
),
(
    'Networking',
    'Routers and networking hardware',
    TRUE,
    1,
    1
);

-- =========================================================
-- PRODUCTS
-- =========================================================

INSERT INTO products (
    product_name,
    product_code,
    product_description,
    product_image_url,
    recommended_price,
    unit_price,
    tax_rate,
    category_id,
    is_active,
    created_by,
    updated_by
)
VALUES
(
    'MacBook Pro 16',
    'PROD-202605-1001',
    'Apple MacBook Pro 16-inch M3',
    'https://images.unsplash.com/photo-1517336714739-489689fd1ca8',
    79999.00,
    75999.00,
    21.00,
    2,
    TRUE,
    1,
    1
),
(
    'Dell XPS 15',
    'PROD-202605-1002',
    'Dell XPS professional laptop',
    'https://images.unsplash.com/photo-1496181133206-80ce9b88a853',
    55999.00,
    52999.00,
    21.00,
    2,
    TRUE,
    1,
    1
),
(
    'iPhone 15 Pro',
    'PROD-202605-1003',
    'Apple iPhone 15 Pro smartphone',
    'https://images.unsplash.com/photo-1592750475338-74b7b21085ab',
    34999.00,
    32999.00,
    21.00,
    3,
    TRUE,
    1,
    1
),
(
    'Samsung Galaxy S24',
    'PROD-202605-1004',
    'Samsung flagship smartphone',
    'https://images.unsplash.com/photo-1610945265064-0e34e5519bbf',
    28999.00,
    26999.00,
    21.00,
    3,
    TRUE,
    1,
    1
),
(
    'PlayStation 5',
    'PROD-202605-1005',
    'Sony PlayStation 5 console',
    'https://images.unsplash.com/photo-1606813907291-d86efa9b94db',
    15999.00,
    14999.00,
    21.00,
    4,
    TRUE,
    1,
    1
),
(
    'Xbox Series X',
    'PROD-202605-1006',
    'Microsoft Xbox Series X console',
    'https://images.unsplash.com/photo-1621259182978-fbf93132d53d',
    14999.00,
    13999.00,
    21.00,
    4,
    TRUE,
    1,
    1
),
(
    'Logitech MX Master 3S',
    'PROD-202605-1007',
    'Professional wireless mouse',
    'https://images.unsplash.com/photo-1527814050087-3793815479db',
    2999.00,
    2599.00,
    21.00,
    5,
    TRUE,
    1,
    1
),
(
    'Keychron K8',
    'PROD-202605-1008',
    'Mechanical wireless keyboard',
    'https://images.unsplash.com/photo-1511467687858-23d96c32e4ae',
    3299.00,
    2999.00,
    21.00,
    5,
    TRUE,
    1,
    1
),
(
    'Sony WH-1000XM5',
    'PROD-202605-1009',
    'Noise cancelling headphones',
    'https://images.unsplash.com/photo-1505740420928-5e560c06d30e',
    9999.00,
    9299.00,
    21.00,
    7,
    TRUE,
    1,
    1
),
(
    'JBL Charge 5',
    'PROD-202605-1010',
    'Portable Bluetooth speaker',
    'https://images.unsplash.com/photo-1589003077984-894e133dabab',
    4499.00,
    3999.00,
    21.00,
    7,
    TRUE,
    1,
    1
),
(
    'TP-Link Archer AX55',
    'PROD-202605-1011',
    'WiFi 6 router',
    'https://images.unsplash.com/photo-1647427060118-4911c9821b82',
    2999.00,
    2699.00,
    21.00,
    8,
    TRUE,
    1,
    1
),
(
    'LG OLED C3',
    'PROD-202605-1012',
    '55 inch OLED television',
    'https://images.unsplash.com/photo-1593784991095-a205069470b6',
    42999.00,
    39999.00,
    21.00,
    1,
    TRUE,
    1,
    1
);