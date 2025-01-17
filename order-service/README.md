# Order Service

The Order Service is responsible for managing customer orders in the e-commerce application. It provides endpoints to create, retrieve, and update orders.

## Endpoints

- `GET /orders`: Retrieve all orders.
- `GET /orders/{id}`: Fetch a specific order by its ID.
- `POST /orders`: Create a new order.
- `PUT /orders/{id}/status`: Update the status of an existing order.

## Models

### Order

- **Description**: Represents an order placed by a user.

| Field       | Type          | Description                  | Constraints          |
|-------------|---------------|------------------------------|----------------------|
| totalAmount | BigDecimal    | Total amount of the order    | Non-nullable         |
| status      | OrderStatus   | Status of the order          | Enum, Non-nullable   |
| userId      | Long          | ID of the user who placed the order |                  |
| orderItems  | List<OrderItem> | Items included in the order |                      |

### OrderItem

- **Description**: Represents an item in an order.

| Field     | Type       | Description                  | Constraints          |
|-----------|------------|------------------------------|----------------------|
| order     | Order      | The order to which this item belongs | Non-nullable         |
| product   | Product    | The product being ordered    | Non-nullable         |
| quantity  | Integer    | Quantity of the product ordered | Non-nullable         |
| unitPrice | BigDecimal | Price per unit of the product | Non-nullable         |
| subtotal  | BigDecimal | Calculated as `unitPrice * quantity` | Non-nullable         |

## Services

### OrderService

- **Description**: Manages order-related operations.
- **Key Methods**:
  - `getAllOrders()`: Retrieves all orders.
  - `getOrderById(Long id)`: Fetches a specific order by its ID.
  - `createOrder(CreateOrderRequest request)`: Creates a new order.
  - `updateOrderStatus(Long id, OrderStatus newStatus)`: Updates the status of an existing order.
