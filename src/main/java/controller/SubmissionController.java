package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import business.model.Exam;
import business.model.SelectedQuestion;
import business.model.Student;
import business.model.Submission;
import business.services.SubmissionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import utils.DisplayDialog_Notification;

public class SubmissionController implements Initializable {
		private Scene scene = null;
	
		private AnchorPane anchor;
		
		private ObservableList<Submission> observableList ;
		
	  	@FXML
	    private TableColumn<Submission, String> ExamID_Submission=new TableColumn<Submission, String>();

	    @FXML
	    private TableColumn<Submission, Double> Score_Submission =new TableColumn<Submission, Double>();

	    @FXML
	    private TableColumn<Submission, String> StudentID_Submission=new TableColumn<Submission, String>();

	    @FXML
	    private TableColumn<Submission, Integer> TTaken_Submission=new TableColumn<Submission, Integer>() ;
	    
	    @FXML
	    private Button view_Submission=new Button();
	    
	    @FXML
	    private Button delete_Submission=new Button();

	    @FXML
	    private TableView<Submission> tableView_Submission=new TableView<Submission>();
	    @FXML
	    private Label ExamID_SubmissView=new Label();

	    @FXML
	    private Label score_SubmissView=new Label();

	    @FXML
	    private Label studentID_SubmissView= new Label();

	    private static SubmissionManager submissionManager = new SubmissionManager();
	    
	    public static Submission submission_Current ;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
	    	SelectionModel<Submission> selectionModel = tableView_Submission.getSelectionModel();
	    	selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    		if (newValue != null) {
	    			submission_Current=newValue;
	    			delete_Submission.setVisible(true);
	    			view_Submission.setVisible(true);
	    		}
	    	});	    	
	    }catch (Exception e) {
		    e.printStackTrace();
	    }
		loadData_Submission();
		if(submission_Current!=null) {
			 load_SubmissView() ;
		}
	}
	
	private void load_SubmissView() {
		studentID_SubmissView.setText(submission_Current.getStudent().getStudentID());
		ExamID_SubmissView.setText(submission_Current.getExam().getExamID().toString());
		score_SubmissView.setText(String.valueOf(submission_Current.getScore()));
		
		for(SelectedQuestion submissSelect : submissionManager.getSelectedQuestions(submission_Current)) {
			for(String question : submissSelect.getQuestionID()) {
				
			}
			for(String answer : submissSelect.getAnswers()) {
				System.out.println(answer);
			}
			for(int answerSelect :submissSelect.getSelectedAnswers())
			{
				
			}
		}
		
		
	}
	private void loadData_Submission() {
		List<Submission> listSubmission = submissionManager.getSubmissions(new LoginController().teacher_Current);
		tableView_Submission.setItems(loadStudent_tableView(listSubmission));
	}
	private ObservableList loadStudent_tableView(List<Submission> list) {
	    observableList = FXCollections.observableArrayList(list);	    

	    TTaken_Submission.setCellValueFactory(new PropertyValueFactory<Submission,Integer>("timeTaken")); 
	    Score_Submission.setCellValueFactory(new PropertyValueFactory<Submission,Double>("score"));
	    ExamID_Submission.setCellValueFactory(cellData -> cellData.getValue().getExamID());
	    StudentID_Submission.setCellValueFactory(cellData -> cellData.getValue().getStudentID());
	    
	    return observableList;
	}
    @FXML
    void button_Delete_Submission(ActionEvent event) {
    	Submission sub_Delete = tableView_Submission.getSelectionModel().getSelectedItem();
    	if(!submissionManager.deleteSubmission(sub_Delete.getExam().getExamID().toString())) {	
    		DisplayDialog_Notification.Dialog_Error( "Unsuccessful notification!", "Submission wasn't delete!","Unsuccessful!");
    	}
    	else {
    		tableView_Submission.getItems().remove(sub_Delete);
    		DisplayDialog_Notification.Dialog_Infomation( "Successful notification!", "Submission was deleted!","Successful!");
    	}
    }
    
    @FXML
    void buttonViewSubmission(ActionEvent event) throws IOException {
    	AnchorPane insidePane = new FXMLLoader(getClass().getResource("/fxml/Submiss_view.fxml")).load();
		setAnchor(insidePane);
		scene = (Scene) ((Node) event.getSource()).getScene();
		anchor = (AnchorPane) scene.lookup("#AnchorPaneLayout");
		anchor.getChildren().clear();
		anchor.getChildren().add(insidePane);
    }
    @FXML
    void back_Submission(ActionEvent event) throws IOException {
    	AnchorPane insidePane = new FXMLLoader(getClass().getResource("/fxml/Submiss.fxml")).load();
		setAnchor(insidePane);
		scene = (Scene) ((Node) event.getSource()).getScene();
		anchor = (AnchorPane) scene.lookup("#AnchorPaneLayout");
		anchor.getChildren().clear();
		anchor.getChildren().add(insidePane);
    }
    private void setAnchor(AnchorPane insidePane) {
    	AnchorPane.setTopAnchor(insidePane, 0.0);
    	AnchorPane.setBottomAnchor(insidePane, 0.0);
    	AnchorPane.setRightAnchor(insidePane, 0.0);
    	AnchorPane.setLeftAnchor(insidePane, 0.0);
    }

}