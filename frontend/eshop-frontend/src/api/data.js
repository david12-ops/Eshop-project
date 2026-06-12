export function createMockDatabase() {
  return {
    products: [
      {
        id: "p1",
        name: "Apple iPhone 16",
        description: "Nejnovější model iPhone",
        price: 29990,
        categoryId: "c1",
        stock: 15,
        active: true,
      },
      {
        id: "p2",
        name: "Samsung Galaxy S26",
        description: "Vlajkový model Samsung",
        price: 26990,
        categoryId: "c1",
        stock: 8,
        active: true,
      },
      {
        id: "p3",
        name: "Logitech MX Master 4",
        description: "Bezdrátová ergonomická myš",
        price: 2990,
        categoryId: "c2",
        stock: 25,
        active: true,
      },
    ],

    categories: [
      {
        id: "c1",
        categoryName: "Mobilní telefony",
        active: true,
      },
      {
        id: "c2",
        categoryName: "Počítačové příslušenství",
        active: true,
      },
    ],

    users: [
      {
        userId: "customer-1",
        username: "novakpe1",
        name: "Petr Novák",
        hashedPassword: "$2a$12$uLuDug67K3KI3HxHeCigneEzpxLB1Vpx4k066UxHF8ayI89yO10lC",
        email: "novak@example.com",
        role: "CUSTOMER",
        token: "customer-1_12345678",
      },
      {
        userId: "customer-2",
        username: "svobodaj1",
        name: "Jan Svoboda",
        hashedPassword: "$2a$12$uLuDug67K3KI3HxHeCigneEzpxLB1Vpx4k066UxHF8ayI89yO10lC",
        email: "svoboda@example.com",
        role: "CUSTOMER",
        token: "customer-2_55555555",
      },
      {
        userId: "admin-1",
        username: "admin",
        name: "Administrátor",
        hashedPassword: "$2a$12$uLuDug67K3KI3HxHeCigneEzpxLB1Vpx4k066UxHF8ayI89yO10lC",
        email: "admin@eshop.cz",
        role: "ADMIN",
        token: "admin-1_87654321",
      },
    ],

    carts: [
      {
        userId: "customer-1",
        items: [
          {
            productId: "p1",
            quantity: 1,
          },
          {
            productId: "p3",
            quantity: 2,
          },
        ],
      },
    ],

    orders: [
      {
        id: "o1",
        customerId: "customer-1",
        orderDate: "2026-01-20",
        status: "PAID",
        totalAmount: 35970,
      },
      {
        id: "o2",
        customerId: "customer-1",
        orderDate: "2026-02-05",
        status: "PENDING",
        totalAmount: 2990,
      },
    ],

    orderItems: [
      {
        id: "oi1",
        orderId: "o1",
        productId: "p1",
        quantity: 1,
        unitPrice: 29990,
      },
      {
        id: "oi2",
        orderId: "o1",
        productId: "p3",
        quantity: 2,
        unitPrice: 2990,
      },
      {
        id: "oi3",
        orderId: "o2",
        productId: "p3",
        quantity: 1,
        unitPrice: 2990,
      },
    ],
  };
}