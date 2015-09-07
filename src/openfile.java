import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;


public class openfile extends JFrame {

	private int numberoffilesattacted;
	private JPanel contentPane;
	private String[] files;
	private JFileChooser filechooser;
	private File AttachedFile;
	private JLabel lblNewLabel;
	private JLabel lblStatus;
	private JTextArea openedfile;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					openfile frame = new openfile();
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
	public openfile() {
		numberoffilesattacted = 0;
		files = new String[10];
		setIconImage(new ImageIcon(this.getClass().getResource("/RFID Signal-50.png")).getImage());
		setTitle("Easy Host File Hosting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnOpenFile = new JButton("Open File");
		btnOpenFile.setIcon(new ImageIcon(this.getClass().getResource("/Add File-50.png")));
		btnOpenFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filechooser = new JFileChooser();
				filechooser.setAcceptAllFileFilterUsed(true);
				int returnVal = filechooser.showDialog(null,"Select");
				if (returnVal == JFileChooser.APPROVE_OPTION && numberoffilesattacted<10) {
					AttachedFile = filechooser.getSelectedFile();
					openedfile.append(AttachedFile.getAbsolutePath()+"\n");
					files[numberoffilesattacted] = AttachedFile.getAbsolutePath();
					numberoffilesattacted++;
				}
				filechooser.setSelectedFile(null);
			}
		});
		
		lblNewLabel = new JLabel("Easy Host");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 32));
		
		lblStatus = new JLabel("Server Standby");
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 16));
		
		JScrollPane scrollPane = new JScrollPane();
		
		openedfile = new JTextArea();
		openedfile.setEditable(false);
		scrollPane.setViewportView(openedfile);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setIcon(new ImageIcon(this.getClass().getResource("/Broom-50.png")));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openedfile.setText("");
				files = new String[10];
				numberoffilesattacted = 0;
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JToggleButton tglbtnStartHosting = new JToggleButton("Start Hosting");
		tglbtnStartHosting.setIcon(new ImageIcon(this.getClass().getResource("/Play-50.png")));
		tglbtnStartHosting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tglbtnStartHosting.isSelected()){
					String list = "";
					for(int i=0;i<numberoffilesattacted;i++){
						File f = new File(files[i]);
						if(f.exists()){
							list = list.concat("<a href=\"http://"+getipaddress.lookupLocalAddress()+"/"+f.getName()+"\">"+f.getName()+"</a><br>");
						}
					}
					String index = "<!DOCTYPE HTML><html><head><title>Eventually by HTML5 UP</title><meta charset=\"utf-8\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\" /><!--[if lte IE 8]><script src=\"assets/js/ie/html5shiv.js\"></script><![endif]--><link rel=\"stylesheet\" href=\"assets/css/main.css\" /><!--[if lte IE 8]><link rel=\"stylesheet\" href=\"assets/css/ie8.css\" /><![endif]--><!--[if lte IE 9]><link rel=\"stylesheet\" href=\"assets/css/ie9.css\" /><![endif]--></head><body background = \"bg02.jpg\"><!-- Header --><header id=\"header\"><h1>Easy Host</h1><p>A simple way for sharing file over Network</p></header><!-- Signup Form -->"+list+"<!-- Scripts --><!--[if lte IE 8]><script src=\"assets/js/ie/respond.min.js\"></script><![endif]--><script src=\"assets/js/main.js\"></script></body></html>";
					FileWriter fileWriter;
					try {
						fileWriter = new FileWriter(new File("index.html"));
						fileWriter.write(index);
						fileWriter.flush();
						fileWriter.close();
						//System.out.println(index);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileserver.hostfiles(files, numberoffilesattacted);
					lblStatus.setForeground(Color.GREEN);
					lblStatus.setText("Hosting Files...");
					label.setText("Use IP :- "+getipaddress.lookupLocalAddress());
					tglbtnStartHosting.setIcon(new ImageIcon(this.getClass().getResource("/Stop-50.png")));
					tglbtnStartHosting.setText("Stop Hosting");
				}
				else{
					fileserver.stophost();
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Hosting Stopped");
					label.setText("");
					tglbtnStartHosting.setIcon(new ImageIcon(this.getClass().getResource("/Play-50.png")));
					tglbtnStartHosting.setText("Start Hosting");
				}
			}
		});
		
		JButton btnOpenServer = new JButton("Open Server");
		btnOpenServer.setIcon(new ImageIcon(this.getClass().getResource("/Open in Browser-50.png")));
		btnOpenServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("http://localhost/"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnOpenServer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		label = new JLabel("");
		
		JLabel lblNewLabel_1 = new JLabel("",new ImageIcon(this.getClass().getResource("/RFID Signal-50big.png")),JLabel.CENTER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(74)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
					.addGap(81))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(236)
					.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
					.addGap(51)
					.addComponent(btnOpenServer)
					.addGap(81))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(237)
					.addComponent(tglbtnStartHosting, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(256))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(76)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOpenFile)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
							.addGap(59)
							.addComponent(btnClear)
							.addGap(85))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(225))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnClear)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnOpenFile)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(16)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblStatus)
							.addGap(17)
							.addComponent(tglbtnStartHosting))
						.addComponent(btnOpenServer))
					.addGap(37))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
System.out.println(:hello");
