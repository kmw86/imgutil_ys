package img_util;

import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("연세대학교대량발급사진변환");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 432, 219);
		contentPane.add(panel);
		panel.setLayout(null);
		
		

		JButton btnNewButton = new JButton("변환");
		btnNewButton.setBounds(313, 165, 105, 27);
		btnNewButton.setToolTipText("300KB이하 및 PNG TO JPG 변환");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("삭제");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("")||textField.getText()==null){
					JOptionPane.showMessageDialog(null, "사진폴더를 선택해주세요.");
					return;
				}
				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				Test001 test001=new Test001();
				String msg="";
				try {
					test001.method00(textField.getText(),textField_1.getText());
					msg="삭제완료";
				} catch (IOException e1) {
					logger.debug(e1.getMessage());
					msg="에러발생"+e1.getMessage();
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(null, msg);
			}
		});
		btnNewButton_1.setBounds(313, 12, 105, 27);
		panel.add(btnNewButton_1);
		
		textField = new JTextField();
		textField.setBounds(131, 51, 287, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("사진폴더선택");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String folderPath=method02();
					textField.setText(folderPath);
				} catch (IllegalArgumentException | ImagingOpException e1) {
					logger.debug(e1.getMessage());
				}
			}
		});
		btnNewButton_2.setBounds(11, 50, 119, 27);
		panel.add(btnNewButton_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(185, 97, 233, 24);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("기등록사진리스트선택");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath=method03();
				textField_1.setText(filePath);
			}
		});
		btnNewButton_3.setBounds(11, 96, 173, 27);
		panel.add(btnNewButton_3);
		
		JLabel label = new JLabel("* 기등록사진파일 삭제");
		label.setBounds(14, 12, 150, 26);
		panel.add(label);
		
		JLabel lblNewLabel = new JLabel("* 사진파일변환(300KB이하 및 PNG TO JPG변환)");
		lblNewLabel.setBounds(14, 169, 299, 18);
		lblNewLabel.setToolTipText("300KB이하 및 PNG TO JPG 변환");
		panel.add(lblNewLabel);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				try {
					method01();
				} catch (IllegalArgumentException | ImagingOpException | IOException | InterruptedException e1) {
					logger.debug(e1.getMessage());
				}
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(null, "완료");
			}
		});

	}

	private void method01() throws IllegalArgumentException, ImagingOpException, IOException, InterruptedException {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("폴더 선택");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				logger.info("You selected the directory: " + jfc.getSelectedFile());
				String originPath = jfc.getSelectedFile().toString();
				String savePath = originPath + "\\result";
				File tmp = new File(savePath);
				if (!tmp.isDirectory()) {
					tmp.mkdirs();
				}
				File[] fileArray = jfc.getSelectedFile().listFiles();
				long startTime=System.currentTimeMillis();
				for (File file : fileArray) {
					new ImgUtil().method02(file, savePath);
				}
				long endTime=System.currentTimeMillis();
				logger.info("----- "+((endTime-startTime)/1000)+"초 -----");
			}
		}
		
	}
	
	private String method02(){
		String result="";
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("폴더 선택");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			logger.info("You selected the directory: " + jfc.getSelectedFile());
			result=jfc.getSelectedFile().toString();
		}
		return result;
	}
	
	private String method03(){
		String result="";
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("파일 선택");
		jfc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			logger.info("You selected the file: " + jfc.getSelectedFile());
			result=jfc.getSelectedFile().toString();
		}
		return result;
	}
}
