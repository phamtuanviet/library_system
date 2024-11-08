package com.jetbrains.librarysystem.controller;

import com.google.zxing.WriterException;
import com.jetbrains.librarysystem.alert.Notification;
import com.jetbrains.librarysystem.api.DocumentsApiService;
import com.jetbrains.librarysystem.dao.DocumentDao;
import com.jetbrains.librarysystem.dao.TransactionDao;
import com.jetbrains.librarysystem.dao.TransactionDisplayDao;
import com.jetbrains.librarysystem.dao.UserDao;
import com.jetbrains.librarysystem.data.DocumentData;
import com.jetbrains.librarysystem.data.TransactionDisplay;
import com.jetbrains.librarysystem.data.UserData;
import com.jetbrains.librarysystem.gui.CardDocument;
import com.jetbrains.librarysystem.gui.CardUser;
import com.jetbrains.librarysystem.qr.QrImage;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

public class DashBoardController extends BaseController implements Initializable {

    @FXML
    private Button document_action_btn;

    @FXML
    private AnchorPane document_action_frame;

    @FXML
    private TextField document_author_text;

    @FXML
    private Button document_btn;

    @FXML
    private TextField document_category_text;

    @FXML
    private FlowPane document_content;

    @FXML
    private TextField document_id_text;

    @FXML
    private ImageView document_image_view;

    @FXML
    private TextField document_quantity_text;

    @FXML
    private ScrollPane document_scrollPane;

    @FXML
    private StackPane document_stack;

    @FXML
    private Button document_swap;

    @FXML
    private TextField document_titile_text;

    @FXML
    private Button home_btn;

    @FXML
    private Button document_qr_back;

    @FXML
    private AnchorPane document_qr_frame;

    @FXML
    private ImageView document_qr_image_view;

    @FXML
    private StackPane home_stack;

    @FXML
    private ImageView image_home;

    @FXML
    private TextField search_document;

    @FXML
    private TextField search_user;

    @FXML
    private Button transaction_querry_btn_option;

    @FXML
    private Button transaction_borrow_btn_option;

    @FXML
    private Button transaction_return_btn_option;

    @FXML
    private AnchorPane transaction_borrow_option;

    @FXML
    private Button transaction_btn;

    @FXML
    private TableColumn<TransactionDisplay, Date> transaction_column_borrowDate_querry;

    @FXML
    private TableColumn<TransactionDisplay, String> transaction_column_titleDocument_querry;

    @FXML
    private TableColumn<TransactionDisplay, Long> transaction_column_transactionId_querry;

    @FXML
    private TableColumn<TransactionDisplay, Long> transaction_column_userId_querry;

    @FXML
    private TableColumn<TransactionDisplay, String> transaction_column_userName_querry;

    @FXML
    private TableColumn<TransactionDisplay, Date> transaction_column_borrowDate_return;

    @FXML
    private TableColumn<DocumentData, String> transaction_column_document_author_borrow;

    @FXML
    private TableColumn<DocumentData, String> transaction_column_document_category_borrow;

    @FXML
    private TableColumn<DocumentData, Long> transaction_column_document_id_borrow;

    @FXML
    private TableColumn<DocumentData, Integer> transaction_column_document_quantity_borrow;

    @FXML
    private TextField transaction_userId_select_borrow;

    @FXML
    private TextField transaction_documentId_select_borrow;

    @FXML
    private TableView<DocumentData> transaction_document_table;

    @FXML
    private TableView<UserData> transaction_user_table;

    @FXML
    private TextField transaction_id_text_return;

    @FXML
    private TableView<TransactionDisplay> transaction_table_return;

    @FXML
    private TableView<TransactionDisplay> transaction_table_querry;

    @FXML
    private TableColumn<DocumentData, String> transaction_column_document_title_borrow;

    @FXML
    private TableColumn<TransactionDisplay, Date> transaction_column_returnDate_querry;

    @FXML
    private TableColumn<TransactionDisplay, String> transaction_column_status_querry;

    @FXML
    private TableColumn<TransactionDisplay, String> transaction_column_titleDocument_return;

    @FXML
    private TableColumn<TransactionDisplay, Long> transaction_column_transactionId_return;

    @FXML
    private TableColumn<TransactionDisplay, Long> transaction_column_userID_return;

    @FXML
    private TableColumn<TransactionDisplay, String> transaction_column_userName_return;

    @FXML
    private TableColumn<UserData, Date> transaction_column_user_birthDate_borrow;

    @FXML
    private TableColumn<UserData, String> transaction_column_user_firstName_borrow;

    @FXML
    private TableColumn<UserData, String> transaction_column_user_gender_borrow;

    @FXML
    private TableColumn<UserData, Long> transaction_column_user_id_borrow;

    @FXML
    private TableColumn<UserData, String> transaction_column_user_lastName_borrow;

    @FXML
    private DatePicker transaction_date_text_borrow;

    @FXML
    private DatePicker transaction_date_text_return;

    @FXML
    private Label totalDocumentsText;

    @FXML
    private Label totalUsersText;

    @FXML
    private Label totalTransactionsText;

    @FXML
    private AnchorPane transaction_querry_option;

    @FXML
    private AnchorPane transaction_return_option;

    @FXML
    private TextField transaction_search_querry;

    @FXML
    private TextField transaction_search_user_borrow;

    @FXML
    private TextField transaction_serach_document_borrow;

    @FXML
    private TextField transaction_search_return;

    @FXML
    private StackPane transaction_stack;

    @FXML
    private Button user_action_btn;

    @FXML
    private AnchorPane user_action_frame;

    @FXML
    private Button user_btn;

    @FXML
    private FlowPane user_content;

    @FXML
    private DatePicker user_date_of_birth_text;

    @FXML
    private TextField user_firstName_text;

    @FXML
    private ComboBox<String> user_gender_text;

    @FXML
    private TextField user_id_text;

    @FXML
    private ImageView user_image_view;

    @FXML
    private TextField user_lastName_text;

    @FXML
    private ScrollPane user_scrollPane;

    @FXML
    private StackPane user_stack;

    private Integer totalUsers;
    private Integer totalDocuments;
    private Integer totalTransactions;

    /* Hiển thị số Object có trong database trên màn hình Home trong DashBoard. */
    private void displayTotal() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                totalUsers = userDao.countTotalUsers();
                totalDocuments = documentDao.countTotalDocuments();
                totalTransactions = transactionDao.countTotalTransactions();
                return null;
            }
        };
        task.setOnSucceeded(_ -> Platform.runLater(() -> {
            totalUsersText.setText(String.valueOf(totalUsers));
            totalDocumentsText.setText(String.valueOf(totalDocuments));
            totalTransactionsText.setText(String.valueOf(totalTransactions));
        }));

        Thread thread = new Thread(task);
        thread.start();
    }


    String[] pathImageHome = {"src/main/resources/image/home/download.jpg", "src/main/resources/image/home/download (1).jpg"
            , "src/main/resources/image/home/download (2).jpg", "src/main/resources/image/home/download (3).jpg"
            , "src/main/resources/image/home/download (4).jpg"};

    /* Xử lý sự kiện đổi màn hình DashBoard và thay đổi css nút được chọn. */
    private void setActivePane(StackPane activePane, Button activeButton, StackPane[] otherPanes, Button[] otherButtons) {
        activePane.setVisible(true);
        for (StackPane pane : otherPanes) {
            pane.setVisible(false);
        }

        activeButton.setStyle("-fx-background-color: #4083ac;");

        for (Button button : otherButtons) {
            button.setStyle("-fx-background-color: #bdc3c7");
        }


    }

    /* Kiểm tra xem đang chọn update hay add trong mục user. */
    private boolean isUpdate = true;

    private final DocumentsApiService documentsApiService = new DocumentsApiService();

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    /* Danh sách trong gender. */
    private final String[] genderList = {"Male", "Female"};

    /* List các ảnh để hiện thị trong phần ở DashBoard. */
    private final Image[] imageList = new Image[pathImageHome.length];
    private int currentIndexImage = 0;

    /* Gắn ảnh mà khung trong phần Home. */
    public void createImageHome() {
        for (int i = 0; i < pathImageHome.length; i++) {
            File imageFile = new File(pathImageHome[i]);
            Image image = new Image(imageFile.toURI().toString());
            imageList[i] = image;
        }
    }

    /* Thay đổi ảnh và hiệu ứng Fade khi chuyển ảnh. */
    private void changeImage() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), image_home);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            currentIndexImage = (currentIndexImage + 1) % pathImageHome.length;
            image_home.setImage(imageList[currentIndexImage]);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), image_home);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }

    public void userGenderList() {
        List<String> genderL = Arrays.asList(genderList);
        ObservableList<String> genderList = FXCollections.observableArrayList(genderL);
        user_gender_text.setItems(genderList);
    }

    /* Danh sách user để hiện thị trong phần User. */
    private ObservableList<UserData> list_user_data = FXCollections.observableArrayList();

    public void removeUser(UserData userData) {
        list_user_data.remove(userData);
    }

    private final UserDao userDao = UserDao.getInstance();

    /* Hiển thị phần nội dung trong mục Transaction. */
    private void setVisableTransaction(AnchorPane anchorPane, AnchorPane[] anchorPanes) {
        anchorPane.setVisible(true);
        for (AnchorPane pane : anchorPanes) {
            pane.setVisible(false);
        }
    }

    /* Quay lại phần chọn nội dung trong Transaction. */
    public void backToTransactionOption() {
        transaction_borrow_option.setVisible(false);
        transaction_querry_option.setVisible(false);
        transaction_return_option.setVisible(false);
        isReturn = false;
    }

    /* Nghe sự kiện đổi Form trong Transaction. */
    public void switchFormTransaction(ActionEvent event) {
        Object source = event.getSource();
        if (source == transaction_borrow_btn_option) {
            isReturn = false;
            searchDocumentsListDb("", list_document_transaction);
            searchUsersByValue("", list_user_transaction);
            setVisableTransaction(transaction_borrow_option
                    , new AnchorPane[]{transaction_querry_option, transaction_return_option});
        } else if (source == transaction_return_btn_option) {
            searchTransactionByValue("", true);
            isReturn = true;
            setVisableTransaction(transaction_return_option
                    , new AnchorPane[]{transaction_querry_option, transaction_borrow_option});
        } else if (source == transaction_querry_btn_option) {
            searchTransactionByValue("", false);
            isReturn = false;
            setVisableTransaction(transaction_querry_option
                    , new AnchorPane[]{transaction_borrow_option, transaction_return_option});
        }
    }


    /* Đổi Form nội dung của DashBoard. */
    public void switchForm(ActionEvent event) {
        Object source = event.getSource();
        if (source == home_btn) {
            setActivePane(home_stack, home_btn, new StackPane[]{user_stack, transaction_stack, document_stack}
                    , new Button[]{user_btn, transaction_btn, document_btn});
            displayTotal();
        } else if (source == user_btn) {
            setActivePane(user_stack, user_btn, new StackPane[]{home_stack, transaction_stack, document_stack}
                    , new Button[]{home_btn, transaction_btn, document_btn});
            userGenderList();
            search_user.clear();

        } else if (source == document_btn) {
            setActivePane(document_stack, document_btn, new StackPane[]{home_stack, user_stack, transaction_stack}
                    , new Button[]{home_btn, user_btn, transaction_btn});

            search_document.clear();
        } else if (source == transaction_btn) {
            setActivePane(transaction_stack, transaction_btn, new StackPane[]{home_stack, user_stack, document_stack}
                    , new Button[]{home_btn, user_btn, document_btn});
        }
    }

    /* Mở màn hình sự kiện add hoặc update của user. */
    public void showUserActionFrame() {
        user_scrollPane.setEffect(new BoxBlur(5, 5, 3));
        user_action_frame.setVisible(true);
        if (isUpdate) {
            user_action_btn.setText("Update");
        } else {
            user_action_btn.setText("Add");
        }
    }

    /* Lấy dự liệu user đang chọn và cho vào khung sự kiện. */
    public void setSelectedUser(UserData userData) {
        user_id_text.setText(String.valueOf(userData.getId()));
        user_lastName_text.setText(String.valueOf(userData.getLastName()));
        user_firstName_text.setText(String.valueOf(userData.getFirstName()));
        user_gender_text.setValue(String.valueOf(userData.getGender()));
        user_date_of_birth_text.setValue(userData.getDateOfBirth().toLocalDate());

        try {
            File imageFile = new File("src/main/resources/" + userData.getImage());
            Image image = new Image(imageFile.toURI().toString(), true);
            UserDao.urlImageCurrent = userData.getImage();
            user_image_view.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Sử lý chèn ảnh vào và tao bản sao ảnh đến phần resource để xử lý trong database. */
    public void insertImageBtn() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files"
                , "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String projectPath = System.getProperty("user.dir");
            File userFolder = new File(projectPath, "src/main/resources/image/user");
            File destinationFile = new File(userFolder, file.getName());
            String fileName = file.getName();

            int counter = 1;
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            String extension = fileName.substring(fileName.lastIndexOf("."));

            while (destinationFile.exists()) {
                String newFileName = baseName + counter + extension;
                destinationFile = new File(userFolder, newFileName);
                counter++;
            }

            try {
                UserDao.urlImageCurrent = "image/user/" + destinationFile.getName();
                Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                File imageFile = new File("src/main/resources/" + UserDao.urlImageCurrent);
                Image image = new Image(imageFile.toURI().toString(), true);
                user_image_view.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* Thực hiện hành động cập nhập trong database. */
    public void actionUser() {
        if (isUpdate) {
            updateUser();
            closUserActionFrame();
        } else {
            addUser();
            closUserActionFrame();
        }
    }

    /* Tìm User theo giá trị trong ô tìm kiếm (Áp dụng cả mục User và Transaction(borrow)). */
    public void searchUsersByValue(String newValue, ObservableList<UserData> listUsers) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    List<UserData> result = userDao.searchUsers(newValue);
                    Platform.runLater(() -> {
                        listUsers.clear();
                        listUsers.addAll(result);
                    });
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Mở khung điền Add user. */
    public void userAddBtnAction() {
        isUpdate = false;
        clearFormUser();
        showUserActionFrame();
    }

    /* Xóa dữ liệu trong khung điền thông tin. */
    private void clearFormUser() {
        user_id_text.clear();
        user_lastName_text.clear();
        user_firstName_text.clear();
        user_gender_text.setValue(null);
        user_date_of_birth_text.setValue(null);

        user_image_view.setImage(null);
    }

    private void updateUser() {
        boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                , "Are you sure you want to update this user?");
        if (confirmed) {
            if (user_firstName_text.getText().isEmpty() || user_lastName_text.getText().isEmpty()
                    || user_firstName_text.getText().isEmpty() || user_date_of_birth_text.getValue() == null
                    || user_gender_text.getValue() == null || user_image_view.getImage() == null) {
                Notification.showAlert(Alert.AlertType.ERROR, "Error Message"
                        , "Please fill the required fields");
            } else {
                Task<Void> newTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        userDao.updateUser(Long.parseLong(user_id_text.getText()), user_firstName_text.getText()
                                , user_lastName_text.getText(), user_date_of_birth_text.getValue()
                                , user_gender_text.getValue(), UserDao.urlImageCurrent);
                        return null;
                    }
                };

                newTask.setOnSucceeded(_ -> {
                    UserDao.urlImageCurrent = null;
                    Platform.runLater(() ->
                            Notification.showAlert(Alert.AlertType.INFORMATION, "Success"
                                    , "User updated successfully!"
                            ));

                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            List<UserData> result = userDao.searchUsers(search_user.getText());
                            Platform.runLater(() -> {
                                list_user_data.clear();
                                list_user_data.addAll(result);
                            });
                            return null;
                        }
                    };

                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();

                });

                newTask.setOnFailed(updateEvent -> {
                    UserDao.urlImageCurrent = null;
                    Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                            , "Error Message", "Failed to update user!"));
                    updateEvent.getSource().getException().printStackTrace();
                });

                Thread newThread = new Thread(newTask);
                newThread.setDaemon(true);
                newThread.start();
            }
        }
    }

    private void addUser() {
        boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                , "Are you sure you want to add this user?");
        if (confirmed) {
            if (user_firstName_text.getText().isEmpty() || user_lastName_text.getText().isEmpty()
                    || user_firstName_text.getText().isEmpty() || user_date_of_birth_text.getValue() == null
                    || user_gender_text.getValue() == null || user_image_view.getImage() == null) {
                Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill the required fields");
            } else {
                Task<Void> newTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        userDao.addUser(user_firstName_text.getText(), user_lastName_text.getText()
                                , user_date_of_birth_text.getValue(), user_gender_text.getValue(), UserDao.urlImageCurrent);
                        return null;
                    }
                };

                newTask.setOnSucceeded(addEvent -> {
                    Platform.runLater(() -> Notification.showAlert(Alert.AlertType.INFORMATION
                            , "Success", "User added successfully!"));

                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            List<UserData> result = userDao.searchUsers(search_user.getText());
                            Platform.runLater(() -> {
                                list_user_data.clear();
                                list_user_data.addAll(result);
                            });
                            return null;
                        }
                    };

                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();

                });

                newTask.setOnFailed(updateEvent -> {
                    Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                            , "Error Message", "Failed to add user!"));
                    updateEvent.getSource().getException().printStackTrace();
                });

                Thread newThread = new Thread(newTask);
                newThread.setDaemon(true);
                newThread.start();
            }

        }
    }

    /* Lấy dữ liệu user được chọn từ bảng trong mục transaction(borrow). */
    public void selectUserTransaction() {
        UserData userData = transaction_user_table.getSelectionModel().getSelectedItem();
        int num = transaction_user_table.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }
        transaction_userId_select_borrow.setText(String.valueOf(userData.getId()));
    }

    /* Lấy dữ liệu document được chọn từ bảng trong mục transaction(borrow). */
    public void selectDocumentTransaction() {
        DocumentData documentData = transaction_document_table.getSelectionModel().getSelectedItem();
        int num = transaction_document_table.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }
        transaction_documentId_select_borrow.setText(String.valueOf(documentData.getId()));
    }

    /* Đóng form điền thông tin của user. */
    public void closUserActionFrame() {
        user_scrollPane.setEffect(null);
        user_action_frame.setVisible(false);
    }

    /* Kiểm tra xem đang ở mục có sẵn hay không có sẵn trong database. */
    private boolean isAvailableDocument = true;

    /* Danh sách document trong mục Document. */
    private ObservableList<DocumentData> list_document_data = FXCollections.observableArrayList();

    public void removeDocument(DocumentData documentData) {
        list_document_data.remove(documentData);
    }

    private final DocumentDao documentDao = DocumentDao.getInstance();

    /* Tìm document theo giá trị ô search cả phần transaction và document. */
    private void searchDocumentsListDb(String newValue, ObservableList<DocumentData> list) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    List<DocumentData> result = documentDao.searchDocuments(newValue);
                    Platform.runLater(() -> {
                        list.clear();
                        list.addAll(result);
                    });
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Tìm kiếm document từ api. */
    private void searchDocumentsApiService() {
        try {
            if (search_document.getText().equals("") || search_document.getText() == null) {
                defaultStringApi.setLength(0);
                defaultStringApi.append("book");
            } else {
                defaultStringApi.setLength(0);
                defaultStringApi.append(search_document.getText());
            }
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    List<DocumentData> result = documentsApiService.searchDocuments(defaultStringApi.toString());
                    Platform.runLater(() -> {
                        list_document_data.clear();
                        list_document_data.addAll(result);
                    });
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Thay đổi sang dạng có sẵn hoặc không và đổi css của button. */
    public void setIsAvailable() {
        if (isAvailableDocument) {
            isAvailableDocument = false;
            document_swap.getStyleClass().add("document_swap_btn");
            document_swap.getStyleClass().remove("document_swap_btn_");
            document_swap.setText("Document Not Available");
            searchDocumentsApiService();
        } else {
            isAvailableDocument = true;
            document_swap.getStyleClass().add("document_swap_btn_");
            document_swap.getStyleClass().remove("document_swap_btn");
            document_swap.setText("Document Available");
            searchDocumentsListDb(search_document.getText(), list_document_data);
        }
    }

    public boolean getIsAvailable() {
        return isAvailableDocument;
    }

    /* Show qr đến link description của document. */
    public void showQrFrame(String linkDescription) throws IOException, WriterException {
        document_scrollPane.setEffect(new BoxBlur(5, 5, 3));
        document_qr_frame.setVisible(true);
        Image qrImage = QrImage.generateQRCodeImage(linkDescription);
        document_qr_image_view.setImage(qrImage);
    }

    /* Hiển thị khung sự kiện của phần document. */
    public void showDocumentActionFrame() {
        document_scrollPane.setEffect(new BoxBlur(5, 5, 3));
        document_action_frame.setVisible(true);
        if (isAvailableDocument) {
            document_action_btn.setText("Update");
        } else {
            document_action_btn.setText("Add");
        }
    }

    /* Lấy dữ liệu document đang được chọn để điền vào form sự kiện. */
    public void setSelectedDocument(DocumentData documentData) {
        document_id_text.setText(String.valueOf(documentData.getId()));
        document_titile_text.setText(String.valueOf(documentData.getTitle()));
        document_author_text.setText(String.valueOf(documentData.getAuthor()));
        document_category_text.setText(String.valueOf(documentData.getCategory()));
        DocumentDao.urlImageCurrent = documentData.getImage();
        DocumentDao.urlDescriptionCurrent = documentData.getLinkDescription();
        if (isAvailableDocument) {
            document_quantity_text.setText(String.valueOf(documentData.getQuantity()));
        }
        try {
            Image image;
            if (documentData.getImage() == null || documentData.getImage().isEmpty()) {
                File imageFile = new File("src/main/resources/image/others/defaultbook.jpg");
                image = new Image(imageFile.toURI().toString());
            } else {
                image = new Image(documentData.getImage());
            }
            document_image_view.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Đóng form sự kiện của document. */
    public void closeDocumentActionFrame() {
        document_scrollPane.setEffect(null);
        document_action_frame.setVisible(false);
    }

    public void closeDocumentQrFrame() {
        document_scrollPane.setEffect(null);
        document_qr_frame.setVisible(false);
    }

    /* Sử lý lại phần đóng sự kiện vì phát sinh do đa luồng
    (khi update và add document thì nó đóng trược khi được lấy dữ liệu truyền vào database. */
    public void closeDocumentFrame() {
        closeDocumentActionFrame();
        document_quantity_text.clear();
    }

    /* Chech xem mục điền số lượng có hợp lệ không. */
    private boolean checkquantityValid(String str) {
        return str.matches("\\d+") && !str.matches("0+");
    }

    /* Thực hiện hành động (add hoặc update) của document. */
    public void actionDocument() {
        if (isAvailableDocument) {
            updateDocument();
            closeDocumentActionFrame();
        } else {
            addDocument();
            closeDocumentActionFrame();
        }
    }

    private void addDocument() {
        boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                , "Are you sure you want to add this document?");
        if (confirmed) {
            if (document_quantity_text.getText().isEmpty()) {
                Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill the quantity field");
            } else {
                if (!checkquantityValid(document_quantity_text.getText())) {
                    Notification.showAlert(Alert.AlertType.ERROR, "Error Message"
                            , "Invalid quantity, request must be a positive integer");
                    return;
                }
                if (documentDao.checkExsitingById(document_id_text.getText())) {
                    Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Document Already Exists");
                    return;
                }
                Task<Void> newTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        documentDao.addDocument(document_id_text.getText(), document_titile_text.getText()
                                , document_author_text.getText(), document_category_text.getText()
                                , Integer.parseInt(document_quantity_text.getText()), DocumentDao.urlImageCurrent
                                , DocumentDao.urlDescriptionCurrent);
                        return null;
                    }
                };

                newTask.setOnSucceeded(addEvent -> {
                    document_quantity_text.clear();
                    DocumentDao.urlImageCurrent = null;
                    Platform.runLater(() -> Notification.showAlert(Alert.AlertType.INFORMATION, "Success"
                            , "Document added successfully!"));
                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            List<DocumentData> result = documentsApiService.searchDocuments(search_document.getText());
                            Platform.runLater(() -> {
                                list_document_data.clear();
                                list_document_data.addAll(result);
                            });
                            return null;
                        }
                    };

                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();
                });

                newTask.setOnFailed(updateEvent -> {
                    document_quantity_text.clear();
                    DocumentDao.urlImageCurrent = null;
                    Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR, "Error Message"
                            , "Failed to add document!"));
                    updateEvent.getSource().getException().printStackTrace();
                });

                Thread newThread = new Thread(newTask);
                newThread.setDaemon(true);
                newThread.start();
            }

        }
    }

    private void updateDocument() {
        boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                , "Are you sure you want to update this document?");
        if (confirmed) {
            if (document_quantity_text.getText().isEmpty()) {
                Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill the quantity field");
            } else {
                if (!checkquantityValid(document_quantity_text.getText())) {
                    Notification.showAlert(Alert.AlertType.ERROR, "Error Message"
                            , "Invalid quantity, request must be a positive integer");
                } else {
                    Task<Void> newTask = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            documentDao.updateDocument(document_id_text.getText()
                                    , Integer.parseInt(document_quantity_text.getText()));
                            return null;
                        }
                    };

                    newTask.setOnSucceeded(updateEvent -> {
                        document_quantity_text.clear();
                        Platform.runLater(() -> Notification.showAlert(Alert.AlertType.INFORMATION
                                , "Success", "Document updated successfully!"));

                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                List<DocumentData> result = documentDao.searchDocuments(search_document.getText());
                                Platform.runLater(() -> {
                                    list_document_data.clear();
                                    list_document_data.addAll(result);
                                });
                                return null;
                            }
                        };

                        Thread thread = new Thread(task);
                        thread.setDaemon(true);
                        thread.start();

                    });

                    newTask.setOnFailed(updateEvent -> {
                        document_quantity_text.clear();
                        Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                                , "Error Message", "Failed to update document!"));
                        updateEvent.getSource().getException().printStackTrace();
                    });

                    Thread newThread = new Thread(newTask);
                    newThread.setDaemon(true);
                    newThread.start();
                }
            }
        }
    }

    /* Danh sách user để hiện thị trong bảng phần transaction(borrow). */
    private ObservableList<DocumentData> list_document_transaction = FXCollections.observableArrayList();

    /* Danh sách user để hiện thị trong bảng phần transaction(borrow). */
    private ObservableList<UserData> list_user_transaction = FXCollections.observableArrayList();

    /* Lấy giá trị của ô seach document và xử lý một ngoại lệ (khi ô search bỏ trống). */
    private final StringBuilder defaultStringApi = new StringBuilder();

    /* Pause lại khi ô seach document thay đổi quá nhanh(để tránh gọi api quá nhiều). */
    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

    private TransactionDao transactionDao = TransactionDao.getInstance();

    private void initTableUser() {
        transaction_column_user_id_borrow.setCellValueFactory(new PropertyValueFactory<>("id"));
        transaction_column_user_firstName_borrow.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        transaction_column_user_lastName_borrow.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        transaction_column_user_birthDate_borrow.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        transaction_column_user_gender_borrow.setCellValueFactory(new PropertyValueFactory<>("gender"));
        transaction_user_table.setItems(list_user_transaction);

        list_user_transaction.addListener((ListChangeListener<? super UserData>) (change) -> {
            while (change.next()) {
                if (change.wasUpdated()) {
                    transaction_user_table.refresh();
                }
            }
        });
    }

    /* Kiểm tra xem có phải mục return trong phần transaction hay không
    để xem lấy mục transaction từ querry hay return. */
    private boolean isReturn;

    private void initTableDocument() {
        transaction_column_document_id_borrow.setCellValueFactory(new PropertyValueFactory<>("id"));
        transaction_column_document_title_borrow.setCellValueFactory(new PropertyValueFactory<>("title"));
        transaction_column_document_author_borrow.setCellValueFactory(new PropertyValueFactory<>("author"));
        transaction_column_document_category_borrow.setCellValueFactory(new PropertyValueFactory<>("category"));
        transaction_column_document_quantity_borrow.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        transaction_document_table.setItems(list_document_transaction);

        list_document_transaction.addListener((ListChangeListener<? super DocumentData>) (change) -> {
            while (change.next()) {
                if (change.wasUpdated()) {
                    transaction_document_table.refresh();
                }
            }
        });
    }

    private void initTableReturn() {
        transaction_column_transactionId_return.setCellValueFactory(new PropertyValueFactory<>("idTransaction"));
        transaction_column_userID_return.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        transaction_column_userName_return.setCellValueFactory(new PropertyValueFactory<>("userName"));
        transaction_column_titleDocument_return.setCellValueFactory(new PropertyValueFactory<>("titleDocument"));
        transaction_column_borrowDate_return.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        transaction_table_return.setItems(transactionDisplays);
        transactionDisplays.addListener((ListChangeListener<? super TransactionDisplay>) (change) -> {
            if (isReturn) {
                while (change.next()) {
                    if (change.wasUpdated()) {
                        transaction_table_return.refresh();
                    }
                }
            } else {
                while (change.next()) {
                    if (change.wasUpdated()) {
                        transaction_table_querry.refresh();
                    }
                }
            }
        });
    }

    private void initTableQuerry() {
        transaction_column_transactionId_querry.setCellValueFactory(new PropertyValueFactory<>("idTransaction"));
        transaction_column_userId_querry.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        transaction_column_userName_querry.setCellValueFactory(new PropertyValueFactory<>("userName"));
        transaction_column_titleDocument_querry.setCellValueFactory(new PropertyValueFactory<>("titleDocument"));
        transaction_column_borrowDate_return.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        transaction_column_returnDate_querry.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        transaction_column_status_querry.setCellValueFactory(new PropertyValueFactory<>("status"));
        transaction_column_borrowDate_querry.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        transaction_table_querry.setItems(transactionDisplays);
    }

    /* Số lượng của document hiện tại để check khi borrow. */
    private Integer quantityDocumentCurent;

    private LocalDate borrowDate;

    public void actionBorrowDocument() {
        boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                , "Are you sure you want to borrow this document?");
        if (confirmed) {
            if (transaction_userId_select_borrow.getText() == null
                    || transaction_documentId_select_borrow.getText() == null
                    || transaction_documentId_select_borrow.getText().isEmpty()
                    || transaction_date_text_borrow.getValue() == null) {
                Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill the all fields");
                return;
            } else {
                Task<Integer> integerTask = new Task<>() {
                    @Override
                    protected Integer call() throws Exception {
                        return transactionDao.countBorrowedDocuments(Long.parseLong(transaction_userId_select_borrow.getText()));
                    }
                };

                integerTask.setOnSucceeded(workerStateEvent -> {
                    Integer count = integerTask.getValue();
                    if (count >= 3) {
                        Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                                , "Error Message", "You cannot borrow more than 3 documents."));
                        return;
                    } else {
                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                quantityDocumentCurent = documentDao.quantityDocument(transaction_documentId_select_borrow.getText());
                                return null;
                            }
                        };
                        task.setOnSucceeded(event -> {
                            if (quantityDocumentCurent > 0) {
                                transactionDao.borrowDocument(Long.parseLong(transaction_userId_select_borrow.getText())
                                        , transaction_documentId_select_borrow.getText()
                                        , transaction_date_text_borrow.getValue());
                                documentDao.updateDocument(transaction_documentId_select_borrow.getText()
                                        , quantityDocumentCurent - 1);
                                Platform.runLater(() -> Notification.showAlert(Alert.AlertType.INFORMATION
                                        , "Success", "Document borrowed successfully!"));
                                transaction_userId_select_borrow.setText(null);
                                transaction_documentId_select_borrow.setText(null);
                                transaction_date_text_borrow.setValue(null);
                            }
                        });

                        task.setOnFailed(event -> Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                                , "Error Message", "Failed!")));

                        Thread newThread = new Thread(task);
                        newThread.setDaemon(true);
                        newThread.start();

                    }
                });

                integerTask.setOnFailed(event -> Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                        , "Error Message", "Failed!")));

                Thread thread = new Thread(integerTask);
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

    private TransactionDisplayDao transactionDisplayDao = TransactionDisplayDao.getInstance();

    /* Danh sách transaction hiển thị cả trong phần querry và return. */
    private ObservableList<TransactionDisplay> transactionDisplays = FXCollections.observableArrayList();

    /* Tìm kiếm transaction theo giá trị của ô seach. */
    public void searchTransactionByValue(String newValue, boolean isReturn) {
        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    List<TransactionDisplay> result = new ArrayList<>();
                    if (isReturn) {
                        result = transactionDisplayDao.searchTransactionDisplayToReturn(newValue);
                    } else {
                        result = transactionDisplayDao.searchTransactionDisplayToQuerry(newValue);
                    }
                    List<TransactionDisplay> finalResult = result;
                    Platform.runLater(() -> {
                        transactionDisplays.clear();
                        transactionDisplays.addAll(finalResult);
                    });
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Lấy dữ liệu của transaction để tra sách. */
    public void selectTransactionToReturn() {
        TransactionDisplay transactionDisplay = transaction_table_return.getSelectionModel().getSelectedItem();
        int num = transaction_table_return.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }
        transaction_id_text_return.setText(String.valueOf(transactionDisplay.getIdTransaction()));
        borrowDate = transactionDisplay.getBorrowDate().toLocalDate();
    }

    public void actionReturnDocument() {
        boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                , "Are you sure you want to return this document?");
        if (confirmed) {
            if (transaction_id_text_return.getText() == null || transaction_date_text_return.getValue() == null) {
                Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill the all fields");
            } else {
                if (borrowDate.isAfter(transaction_date_text_return.getValue())) {
                    Notification.showAlert(Alert.AlertType.ERROR, "Error Message"
                            , "The return date must be after the borrow date.");
                } else {
                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            transactionDao.returnDocument(Long.parseLong(transaction_id_text_return.getText())
                                    , transaction_date_text_return.getValue());
                            return null;
                        }
                    };
                    task.setOnSucceeded(_ -> {
                        Platform.runLater(() -> Notification.showAlert(Alert.AlertType.INFORMATION
                                , "Success", "Document returned successfully!"));
                        searchTransactionByValue(transaction_search_return.getText(), true);
                        transaction_id_text_return.setText(null);
                        transaction_date_text_return.setValue(null);
                    });

                    task.setOnFailed(_ -> Platform.runLater(() -> Notification.showAlert(Alert.AlertType.ERROR
                            , "Error Message", "Failed!")));

                    Thread newThread = new Thread(task);
                    newThread.setDaemon(true);
                    newThread.start();
                }
            }
        }
    }

    /* Đăng xuất tài khoản. */
    public void logout() {
        try {
            boolean confirmed = Notification.confirmationDialog("Confirmation Message"
                    , "Are you sure you want to logout?");
            if (confirmed) {
                Stage currentStage = (Stage) document_btn.getScene().getWindow();
                currentStage.close();

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createImageHome();
        searchDocumentsListDb("", list_document_data);
        searchUsersByValue("", list_user_data);
        initTableUser();
        initTableDocument();
        initTableReturn();
        initTableQuerry();
        userGenderList();
        displayTotal();
        image_home.setImage(imageList[0]);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), _ -> changeImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        search_user.textProperty().addListener((observable, oldValue, newValue)
                -> searchUsersByValue(newValue, list_user_data));

        transaction_search_querry.textProperty().addListener((observable, oldValue, newValue)
                -> searchTransactionByValue(newValue, false));

        transaction_search_return.textProperty().addListener((observable, oldValue, newValue)
                -> searchTransactionByValue(newValue, true));

        transaction_search_user_borrow.textProperty().addListener((observable, oldValue, newValue)
                -> searchUsersByValue(newValue, list_user_transaction));

        search_document.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.stop();
            pause.setOnFinished(event -> {
                if (isAvailableDocument) {
                    searchDocumentsListDb(newValue, list_document_data);
                } else {
                    searchDocumentsApiService();
                }
            });
            pause.playFromStart();
        });

        transaction_serach_document_borrow.textProperty().addListener((observable, oldValue, newValue)
                -> searchDocumentsListDb(newValue, list_document_transaction));

        list_document_data.addListener((ListChangeListener<? super DocumentData>) change -> {
            document_content.getChildren().clear();
            for (DocumentData documentData : list_document_data) {
                document_content.getChildren()
                        .add(new CardDocument(documentData, this, isAvailableDocument));
            }
        });

        list_user_data.addListener((ListChangeListener<? super UserData>) (change) -> {
            user_content.getChildren().clear();
            for (UserData userData : list_user_data) {
                user_content.getChildren().add(new CardUser(userData, this));
            }
        });

    }
}
