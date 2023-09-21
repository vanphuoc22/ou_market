package com.tdkhoa.oumarket;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Customer;
import pojo.Employee;
import pojo.Order;
import pojo.OrderDetails;
import pojo.Product;
import pojo.Promotion;
import services.PromotionService;
import services.CategoryService;
import services.CustomerService;
import services.EmployeeService;
import services.OrderDetailsService;
import services.OrderService;
import services.ProductService;
import utils.MessageBox;
import pojo.Data;
import com.tdkhoa.oumarket.LoginController;
import java.util.Collection;
import java.util.Iterator;

public class PrimaryController implements Initializable {

    static ProductService pS = new ProductService();
    static CategoryService cS = new CategoryService();
    static EmployeeService eS = new EmployeeService();
    static Product pRow = new Product();
    static Employee eRow = new Employee();
    static LoginController lC = new LoginController();

    static Category cRow = new Category();
    static Promotion proRow = new Promotion();

    static OrderDetailsService oDS = new OrderDetailsService();
    static OrderService oS = new OrderService();
    static CustomerService cusS = new CustomerService();
    static PromotionService promoService = new PromotionService();

    @FXML
    TableView<Product> tbProducts;
    @FXML
    TableView<Product> tbShowProducts;

    @FXML
    TableView<OrderDetails> tbShowOrdersDetail;
    ObservableList<OrderDetails> cartItems = FXCollections.observableArrayList();
    double total = cartItems.stream().mapToDouble(OrderDetails::getTotal).sum();

    @FXML
    TableView<Category> tbCategories;
    @FXML
    TableView<Employee> tbEmployees;
    @FXML
    TableView<Order> tbOrders;
    @FXML
    TableView<Customer> tbCustomers;
    @FXML
    TableView<Promotion> tbPromotions;
    @FXML
    ComboBox<Category> cbCategories;
    @FXML
    TextField txtTotal;
    @FXML
    private Button btnAddEmp;
    @FXML
    TextField txtPhone;
    @FXML
    TextField txtTienKhachDua;
    @FXML
    TextField txtTienTraKhach;
    @FXML
    private Button btnAddSP;
    @FXML
    private Button btnAddCate;

    @FXML
    private Spinner spinner;

//    @FXML private TextField txtSearch;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button btnAddPromotion;
    @FXML
    private TextField txtSearch;

    @FXML
    private VBox sceneVBox;

    @FXML
    private TextField viewUser;
//    

    @FXML
    List<Customer> customers;

    Stage stageOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.txtPhone.textProperty().addListener((observable, oldValue, newValue) -> {
//                this.txtPhone.getText().replaceAll("\\s+", "");
                if (newValue.length() > 10) {
                    this.txtPhone.setText(oldValue);
                }
                try {
                    if (newValue.length() == 10) {
                        this.customers = cusS.getCustomers();
                        Customer c = cusS.findCustomerByPhoneNumber(customers, this.txtPhone.getText());
                        if (c != null) {
                            MessageBox.getBox("Tìm kiếm khách hàng", "Đã tìm thấy khách hàng", Alert.AlertType.CONFIRMATION).show();
                            double tongTien = Double.parseDouble(txtTotal.getText());
                            double discount = tongTien * 0.1;
                            double s = tongTien - discount;
                            txtTotal.setText(Double.toString(s));
                        } else {
                            MessageBox.getBox("Tìm kiếm khách hàng", "Không tồn tại khách hàng có số điện thoại này", Alert.AlertType.ERROR).show();
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.viewUser.setText(lC.savedUsername);
            this.txtTotal.setText("0");
            this.txtTienKhachDua.setText("0");
            this.txtTienTraKhach.setText("0");

            this.customers = cusS.getCustomers();
            this.loadTableProductsColumns();
            this.loadTableCategoriesColumns();
            this.loadTableEmployeesColumns();
            this.loadTableShowProductsColumns();
            this.loadTableShowOrdersDetailColumns();
            this.loadTableOrdersColumns();
            this.loadTableCustomersColumns();
            this.loadTablePromotionsColumns();
//            
            this.loadProductsData(null);
            this.loadCategoriesData(null);
            this.loadEmployeesData(null);

            this.loadCustomerData();

            this.loadOrdersData();
            this.loadPromotionData(null);

        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadProductsData(this.txtSearch.getText().replaceAll("\\s+", ""));
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.txtTienKhachDua.textProperty().addListener(e -> {
            if (!this.txtTienKhachDua.getText().equals("")) {
                double tienTraKhach = Double.parseDouble(this.txtTienKhachDua.getText()) - Double.parseDouble(this.txtTotal.getText());
                this.txtTienTraKhach.setText(Double.toString(tienTraKhach));
            } else {
                this.txtTienTraKhach.setText("0");
            }
        });

        this.cartItems.addListener((ListChangeListener<OrderDetails>) change -> {
            if (!this.cartItems.isEmpty()) {
                this.txtTienKhachDua.setDisable(false);
            } else {
                this.txtTienKhachDua.setDisable(true);
            }
        });
    }

    public void viewAddProduct(ActionEvent evt) {
        this.btnAddSP.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setProducts.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Tạo sản phẩm");
                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadProductsData(null);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void viewAddCategory(ActionEvent evt) throws SQLException {
        this.btnAddCate.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setCategory.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Tạo danh mục");

                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadCategoriesData(null);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void viewAddEmployee(ActionEvent evt) throws SQLException {
        this.btnAddEmp.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setEmloyee.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Thêm nhân viên");

                stage.setOnHidden(e -> {
                    try {
                        loadEmployeesData(null);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void viewAddCustomer(ActionEvent evt) throws SQLException {
        this.btnAddCustomer.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setCustomer.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Thêm khách hàng");

                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadCustomerData();
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void viewAddPromotion(ActionEvent evt) throws SQLException {
        this.btnAddPromotion.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                // Tạo Scene mới
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/setVoucher.fxml"));
                Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Tạo khuyến mãi");

                stage.setOnHidden(e -> {                       //xử lý khi sự kiện stage đóng lại
                    try {
                        loadPromotionData(null);
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    //Hien thi cot trong bang
    private void loadTableShowOrdersDetailColumns() {
        TableColumn<OrderDetails, String> colName = new TableColumn<>("Tên SP");
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        colName.setPrefWidth(170);

        TableColumn<OrderDetails, Double> colQuantity = new TableColumn<>("Số lượng");
        colQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getQuantity()).asObject());

        TableColumn<OrderDetails, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<OrderDetails, Double> colPriceDiscount = new TableColumn<>("Price Discounted");
        colPriceDiscount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPriceDiscount()).asObject());

        TableColumn<OrderDetails, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());

        TableColumn colDel = new TableColumn("Xóa");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Hóa đơn",
                        "Bạn có chắc chắn muốn xóa không ?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        OrderDetails orderDetails = (OrderDetails) cell.getTableRow().getItem();

                        this.tbShowOrdersDetail.getItems().remove(orderDetails);
                        total = cartItems.stream().mapToDouble(OrderDetails::getTotal).sum();
                        txtTotal.setText(Double.toString(total));
                        this.tbShowOrdersDetail.refresh();
                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        this.tbShowOrdersDetail.getColumns().addAll(colName, colQuantity, priceCol, colPriceDiscount, totalCol, colDel);
    }

    private void loadTableOrdersColumns() {
        TableColumn colId = new TableColumn("Mã HĐ");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colOrderDate = new TableColumn("Ngày tạo");
        colOrderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));

        TableColumn colTotal = new TableColumn("Tổng");
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        TableColumn colSelect = new TableColumn("Xem");
        colSelect.setCellFactory(r -> {
            Button btnView = new Button("View");

            btnView.setOnAction(event -> {

                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                Order order = (Order) cell.getTableRow().getItem();
                Data data = new Data();
                data.setId(order.getId());

                try {
                    Stage stage = new Stage();
                    // Tạo Scene mới
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/viewOrderDetails.fxml"));
                    Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                    stage.setScene(scene);
                    stage.setTitle("Xem chi tiết hóa đơn");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableCell c = new TableCell();
            c.setGraphic(btnView);
            return c;
        });

        this.tbOrders.getColumns().addAll(colId, colOrderDate, colTotal, colSelect);
    }

    private void loadTableShowProductsColumns() {

        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);

        TableColumn colCate = new TableColumn("Danh mục");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryName"));

        TableColumn colPrice = new TableColumn("Giá tiền");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));

        TableColumn colQuantity = new TableColumn("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn colAdd = new TableColumn("Thêm");
        colAdd.setCellFactory(r -> {
            Button btn = new Button("Thêm");
            btn.setOnAction(evt -> {
                Button b = (Button) evt.getSource();
                TableCell cell = (TableCell) b.getParent();
                Product p = (Product) cell.getTableRow().getItem();

                Popup popup = new Popup();
                VBox popupContent = new VBox();
                Label quantityLabel = new Label("Số lượng: ");
                TextField quantityTextField = new TextField();
                quantityTextField.setText("0");
                Button saveButton = new Button("Save");
                saveButton.setOnAction(event -> {
                    
                    double sumQuantityProduct = 0;
                    boolean checkAdd = true;
                    try {
                        double quantity = Double.parseDouble(quantityTextField.getText().trim());
                        if (quantity > 0 && p.getQuantity() >= quantity) {
                            if (!p.getUnit().equals("Kg")) { ///Neu khac kg
                                //parse ve int
                                int parseTest = Integer.parseInt(quantityTextField.getText().trim());
                            }
                            if (!cartItems.isEmpty()) { //Neu trong gio hang co san pham\
                                for (OrderDetails cartItem : cartItems) {
                                    if (cartItem.getProduct().getId().equals(p.getId())) { //Neu san pham them vao co trong gio hang
                                        sumQuantityProduct = cartItem.getQuantity() + quantity;
                                        if (sumQuantityProduct <= p.getQuantity()) {
                                            cartItem.setQuantity(sumQuantityProduct);
                                            System.out.println("Cap nhat so luong san pham thanh cong");
                                            System.out.println(cartItem.getProduct().getId());
                                            System.out.println(p.getId());
                                        } else {
                                            MessageBox.getBox("Số lượng sản phẩm", "Quá số lượng kho", Alert.AlertType.WARNING).show();
                                        }
                                        checkAdd = false;
                                        break;
                                    }
                                }
                                if (checkAdd) {
                                    cartItems.add(new OrderDetails(p, quantity));
                                }
                            } else {
                                cartItems.add(new OrderDetails(p, quantity));
                            }
                        }
                        else {
                            MessageBox.getBox("Số lượng sản phẩm", "Quá số lượng kho", Alert.AlertType.WARNING).show();
                        }
                    } catch (NumberFormatException ex) {
                        MessageBox.getBox("Số lượng sản phẩm", "Số lượng nhập phải là số nguyên", Alert.AlertType.WARNING).show();
                    }
                    total = cartItems.stream().mapToDouble(OrderDetails::getTotal).sum();
                    txtTotal.setText(Double.toString(total));

                    this.tbShowOrdersDetail.setItems(cartItems);
                    this.tbShowOrdersDetail.refresh();
                    // Đóng Popup
                    popup.hide();
                    quantityTextField.clear();
                });
                // Thêm các thành phần vào VBox
                popupContent.getChildren().addAll(quantityLabel, quantityTextField, saveButton);

                // Thiết lập nội dung cho Popup
                popup.getContent().addAll(popupContent);

                // Thiết lập sự kiện cho button
                btn.setOnAction(event -> {
                    // Hiển thị Popup tại vị trí của button
                    popup.show(btn.getScene().getWindow(), 500, 500);
//                    popup.show(btn.getScene().getWindow(), 600, 500);
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        this.tbShowProducts.getColumns().addAll(colId, colName, colCate, colPrice, colQuantity, colUnit, colAdd);
    } //Page chọn sản phẩm để thanh toán

    private void loadTableProductsColumns() {
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);

        TableColumn colCate = new TableColumn("Danh mục");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryName"));

        TableColumn colPrice = new TableColumn("Giá tiền");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn colUnit = new TableColumn("Đơn vị");
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));

        TableColumn colQuantity = new TableColumn("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn colDel = new TableColumn("Delete");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Sản phẩm",
                        "Bạn có chắc chắn muốn xóa sản phẩm này không ?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Product p = (Product) cell.getTableRow().getItem();
                        try {
                            if (pS.deleteProduct(p.getId())) {
                                MessageBox.getBox("Sản phẩm", "Xóa thành công", Alert.AlertType.INFORMATION).show();
                                this.loadProductsData(null);
                            } else {
                                MessageBox.getBox("Sản phẩm", "Xóa thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Sản phẩm", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        TableColumn colEdit = new TableColumn("Edit");
        colEdit.setCellFactory(r -> {
            Button btnEdit = new Button("Edit");

            btnEdit.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                pRow = (Product) cell.getTableRow().getItem();
                try {
                    Stage stage = new Stage();
                    // Tạo Scene mới
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixProducts.fxml"));
                    Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                    stage.setScene(scene);
                    stage.setTitle("Chỉnh sửa sản phẩm");
                    stage.setOnHidden(e -> {//xử lý khi sự kiện stage đóng lại
                        try {
                            loadProductsData(null);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
        });

        TableColumn colKM = new TableColumn("Add KM");
        colKM.setCellFactory(r -> {
            Button btnKM = new Button("Add KM");

            btnKM.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                pRow = (Product) cell.getTableRow().getItem();
                try {
                    Stage stage = new Stage();
                    // Tạo Scene mới
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/changePromotion.fxml"));
                    Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                    stage.setScene(scene);
                    stage.setTitle("Thay đổi mã khuyến mãi");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            TableCell c = new TableCell();
            c.setGraphic(btnKM);
            return c;
        });

        this.tbProducts.getColumns().addAll(colId, colName, colCate, colPrice, colQuantity, colUnit, colEdit, colDel, colKM);
        //Page load tất cả các sản phẩm
    }

    private void loadTableEmployeesColumns() {
        TableColumn colId = new TableColumn("Mã nhân viên");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Họ tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colDel = new TableColumn("Xoá");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Xoá");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Nhân viên",
                        "Bạn có chắc muốn xoá nhân viên này?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Employee e = (Employee) cell.getTableRow().getItem();
                        try {
                            if (eS.deleteEmployee(e.getId())) {
                                MessageBox.getBox("Nhân viên", "Xoá thành công", Alert.AlertType.INFORMATION).show();
                                this.loadEmployeesData(null);
                            } else {
                                MessageBox.getBox("Nhân viên", "Xoá thất bại", Alert.AlertType.WARNING).show();
                            }

                        } catch (SQLException ex) {
                            MessageBox.getBox("Nhân viên", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        TableColumn colEdit = new TableColumn("Sửa");
        colEdit.setCellFactory(r -> {
            Button btnEdit = new Button("Sửa");

            btnEdit.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                eRow = (Employee) cell.getTableRow().getItem();
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixEmployee.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Chỉnh sửa thông tin nhân viên");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
        });

        this.tbEmployees.getColumns().addAll(colId, colName, colDel, colEdit);
    }

    private void loadEmployeesData(String kw) throws SQLException {
        List<Employee> employees = eS.getEmployees(kw);

        this.tbEmployees.getItems().clear();
        this.tbEmployees.setItems(FXCollections.observableList(employees));
    }

    private void loadTableCategoriesColumns() {
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colName = new TableColumn("Tên danh mục");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);

        TableColumn colDel = new TableColumn("Delete");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Danh mục",
                        "Bạn có chắc chắn muốn xóa danh mục này không ?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Category c = (Category) cell.getTableRow().getItem();
                        try {
                            if (cS.deleteCategory(c.getId())) {
                                MessageBox.getBox("Danh mục", "Xóa thành công", Alert.AlertType.INFORMATION).show();
                                this.loadCategoriesData(null);
                                this.loadProductsData(null);
                            } else {
                                MessageBox.getBox("Danh mục", "Xóa thất bại", Alert.AlertType.WARNING).show();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Danh mục", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        TableColumn colEdit = new TableColumn("Edit");
        colEdit.setCellFactory(r -> {
            Button btnEdit = new Button("Edit");
            btnEdit.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                cRow = (Category) cell.getTableRow().getItem();
                try {
                    Stage stage = new Stage();
                    // Tạo Scene mới
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixCategory.fxml"));
                    Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                    stage.setScene(scene);
                    stage.setTitle("Chỉnh sửa sản phẩm");
                    stage.setOnHidden(e -> {//xử lý khi sự kiện stage đóng lại
                        try {
                            loadCategoriesData(null);
                            loadProductsData(null);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
        });

        this.tbCategories.getColumns().addAll(colId, colName, colDel, colEdit);
    } //Page load tất cả danh mục

    private void loadTableCustomersColumns() {
        TableColumn colId = new TableColumn("Mã KH");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Họ tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colBirth = new TableColumn("Ngày Sinh");
        colBirth.setCellValueFactory(new PropertyValueFactory("ngaySinh"));

        TableColumn colPhone = new TableColumn("SĐT");
        colPhone.setCellValueFactory(new PropertyValueFactory("phone"));

        TableColumn colPoint = new TableColumn("Điểm");
        colPoint.setCellValueFactory(new PropertyValueFactory("point"));

        this.tbCustomers.getColumns().addAll(colId, colName, colBirth, colPhone, colPoint);
    } //Page load tất cả thông tin khách hàng

    private void loadTablePromotionsColumns() {
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(50);

        TableColumn colName = new TableColumn("Tên mã khuyến mãi");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);

        TableColumn colVal = new TableColumn("% Giảm");
        colVal.setCellValueFactory(new PropertyValueFactory("value"));
        colVal.setPrefWidth(50);

        TableColumn colStart = new TableColumn("Ngày bắt đầu");
        colStart.setCellValueFactory(new PropertyValueFactory("timeStart"));
        colStart.setPrefWidth(170);

        TableColumn colEnd = new TableColumn("Ngày kết thúc");
        colEnd.setCellValueFactory(new PropertyValueFactory("timeEnd"));
        colEnd.setPrefWidth(170);

        TableColumn colDel = new TableColumn("Delete");
        colDel.setCellFactory(r -> {
            Button btn = new Button("Delete");

            btn.setOnAction(evt -> {
                Alert a = MessageBox.getBox("Khuyến mãi",
                        "Bạn có chắc muốn xóa mã khuyến mãi này không ?",
                        Alert.AlertType.CONFIRMATION);
                a.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Promotion pro = (Promotion) cell.getTableRow().getItem();
                        try {
                            if (promoService.deletePromotion(pro.getId())) {
                                MessageBox.getBox("Khuyến mãi", "Xóa thành công", Alert.AlertType.INFORMATION).show();
                                this.loadPromotionData(null);
                            } else {
                                MessageBox.getBox("Khuyến mãi", "Xóa thất bại", Alert.AlertType.WARNING).show();
                            }
                        } catch (SQLException ex) {
                            MessageBox.getBox("Khuyến mãi", ex.getMessage(), Alert.AlertType.WARNING).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });

        TableColumn colEdit = new TableColumn("Edit");
        colEdit.setCellFactory(r -> {
            Button btnEdit = new Button("Edit");

            btnEdit.setOnAction(event -> {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                proRow = (Promotion) cell.getTableRow().getItem();
                try {
                    Stage stage = new Stage();
                    // Tạo Scene mới
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/fixVoucher.fxml"));
                    Scene scene = new Scene(root);// Thiết lập Scene cho Stage mới
                    stage.setScene(scene);
                    stage.setOnHidden(e -> {//xử lý khi sự kiện stage đóng lại
                        try {
                            loadPromotionData(null);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    stage.setTitle("Chỉnh sửa mã khuyến mãi");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            TableCell c = new TableCell();
            c.setGraphic(btnEdit);
            return c;
        });

        this.tbPromotions.getColumns().addAll(colId, colName, colVal, colStart, colEnd, colEdit, colDel);
    } //Page load tất cả mã khuyến mãi

    //Load du lieu
    private void loadProductsData(String kw) throws SQLException {

        List<Product> pros = pS.getProducts(kw);

        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(pros));

        this.tbShowProducts.getItems().clear();
        this.tbShowProducts.setItems(FXCollections.observableList(pros));
    }

    private void loadCategoriesData(String kw) throws SQLException {
        List<Category> cates = cS.getCategories(kw);

        this.tbCategories.getItems().clear();
        this.tbCategories.setItems(FXCollections.observableList(cates));
    }

    private void loadCustomerData() throws SQLException {
        List<Customer> customers = cusS.getCustomers();

        this.tbCustomers.getItems().clear();
        this.tbCustomers.setItems(FXCollections.observableList(customers));
    }

    private void loadOrdersData() throws SQLException {
        List<Order> orders = oS.getOrders();

        this.tbOrders.getItems().clear();
        this.tbOrders.setItems(FXCollections.observableList(orders));
    }

    private void loadPromotionData(String kw) throws SQLException {
        List<Promotion> pros = null;
        try {
            pros = promoService.getPromotions(kw);
        } catch (ParseException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.tbPromotions.getItems().clear();
        this.tbPromotions.setItems(FXCollections.observableList(pros));
    }

//    public void closeView(ActionEvent evt) {
//        stageOut = (Stage) sceneVBox.getScene().getWindow();
//        stageOut.close();
//    }
    public void refreshCart() {
        Double priceRefresh = 0.0;
        this.tbShowOrdersDetail.getItems().clear();
        this.txtTienKhachDua.setText(Double.toString(priceRefresh));
        this.txtTienTraKhach.setText(Double.toString(priceRefresh));
        this.txtTotal.setText(Double.toString(priceRefresh));
    }

    public void savePay() throws SQLException {
        boolean flag = false;
        Double tienTraKhach = Double.parseDouble(this.txtTienTraKhach.getText());
        Double tienKhachDua = Double.parseDouble(this.txtTienKhachDua.getText());
        if (this.cartItems.isEmpty()) {
            MessageBox.getBox("Hóa đơn", "Không có sản phẩm trong giỏ hàng", Alert.AlertType.WARNING).show();
        } else {
            if (tienTraKhach >= 0 && tienKhachDua >= Double.parseDouble(this.txtTotal.getText())) {
                Order o = new Order();
                String phone = txtPhone.getText();
                System.out.println(phone);
                double tongTien = Double.parseDouble(txtTotal.getText());
                if (!phone.isEmpty()) { //Nếu txtPhone không rỗng
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDateTime purchaseDate = LocalDateTime.now();
                    String formattedDateTime = purchaseDate.format(formatter);
                    String purchaseDateSS = formattedDateTime.substring(0, 5);
                    Customer customer = cusS.findCustomerByPhoneNumber(customers, phone);

                    if (customer != null) {
                        String customerBirthDay = customer.getNgaySinh().substring(0, 5);
                        cusS.updatePoint(customer, this.total);
                        if (customerBirthDay.equals(purchaseDateSS) && tongTien >= 1000000) {
                            double discount = tongTien * 0.1;
                            double s = tongTien - discount;
                            o.setTotal(s);
                        } else {
                            o.setTotal(Double.parseDouble(txtTotal.getText()));
                        }
                        flag = true;
                    } else {
                        flag = false;
                        o.setTotal(total);
                        MessageBox.getBox("Tìm kiếm khách hàng", "Không tồn tại khách hàng có số điện thoại này", Alert.AlertType.ERROR).show();
                    }
                } else //Nếu txtPhone rỗng
                {
                    o.setTotal(Double.parseDouble(txtTotal.getText()));
                    flag = true;
                }
                o.setTienKhachDua(Double.parseDouble(txtTienKhachDua.getText()));
                o.setTienTraKhach(Double.parseDouble(txtTienTraKhach.getText()));
                if (flag == true) {
                    oS.addOrder(o);
                    ObservableList<OrderDetails> orderDetailsList = this.tbShowOrdersDetail.getItems();
                    boolean tmp = false;
                    for (OrderDetails oD : orderDetailsList) {
                        if (oDS.saveOderDetails(oD, o)) {
                            tmp = true;
                        } else {
                            tmp = false;
                        }
                    }
                    if (tmp == true) {
                        oDS.updateQuantityInStock(orderDetailsList);
                        this.tbShowOrdersDetail.getItems().clear();
                        loadProductsData(null);
                        loadOrdersData();
                        loadCustomerData();
                        txtTienKhachDua.setText("0");
                        txtTotal.setText("0");
                        txtTienTraKhach.setText("0");
                        MessageBox.getBox("Hóa đơn", "Thêm hóa đơn thành công ", Alert.AlertType.CONFIRMATION).show();
                    } else {
                        MessageBox.getBox("Hóa đơn", "Thêm hóa đơn thất bại", Alert.AlertType.ERROR).show();
                    }
                }

            } else {
                MessageBox.getBox("Hóa đơn", "Vui lòng nhập tiền phù hợp", Alert.AlertType.ERROR).show();
            }
        }
    }

}
