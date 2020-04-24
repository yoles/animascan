package fr.lesueur.yohann.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import fr.lesueur.yohann.entities.Manga;
import fr.lesueur.yohann.entities.Scan;
import fr.lesueur.yohann.impls.MangaServiceImpl;
import fr.lesueur.yohann.impls.ScanVfServiceImpl;
import fr.lesueur.yohann.impls.ScantradServiceImpl;
import fr.lesueur.yohann.services.MangaService;
import fr.lesueur.yohann.services.PropertyService;
import fr.lesueur.yohann.services.ScanService;
import fr.lesueur.yohann.validator.ArgsValidator;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField nameInput;
	private JLabel chapterTxt;
	private JTextField chapterInput;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private String url;
	private MangaService mangaService = new MangaServiceImpl(new ArrayList<Manga>());
	private PropertyService ps;
	private Scan scan = null;
	private Manga m = null;
	private Document doc = null;
	private ScanService scanService = null;
	private int chInput = 0;
	private int totalPage = 0;
	
	private void setMangaAvailable(JComboBox<Object> sourceList) {
			
		Object value = (Object)sourceList.getSelectedItem();
		url = value.toString().split(".net")[0];

		ps = new PropertyService();
		String src = ArgsValidator.checkSourceGUI(ps, url);
		
		url = null;

		scan = new Scan(src);
	
		switch (ps.getCurrentSource()) {
		case "scantrad":
			scanService = new ScantradServiceImpl(scan);
			break;
		case "scanvf":
			scanService = new ScanVfServiceImpl(scan);
			break;	
		default:
			scanService = new ScanVfServiceImpl(scan);
			break;
		}
		
		doc = scanService.connect(null);
		Elements anchors = scanService.getMangaListHTML(doc);
		scanService.addMangaFromHTML(anchors, mangaService);
	}

	public Window() {
		url = null;
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("AnimaScan");
		
		JPanel panel = new JPanel();
		panel.setBounds(38, 12, 621, 437);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel nameTxt = new JLabel("Nom du Manga: ");
		nameTxt.setFont(new Font("Dialog", Font.BOLD, 18));
		nameTxt.setHorizontalAlignment(SwingConstants.LEFT);
		nameTxt.setBounds(41, 12, 261, 29);
		panel.add(nameTxt);
		
		nameInput = new JTextField();
		nameInput.setBounds(41, 44, 250, 22);
		panel.add(nameInput);
		nameInput.setColumns(10);
		
		chapterTxt = new JLabel("Numero du chapitre");
		chapterTxt.setFont(new Font("Dialog", Font.BOLD, 18));
		chapterTxt.setHorizontalAlignment(SwingConstants.LEFT);
		chapterTxt.setBounds(41, 135, 282, 29);
		panel.add(chapterTxt);
		
		chapterInput = new JTextField();
		chapterInput.setColumns(10);
		chapterInput.setBounds(41, 160, 250, 22);
		panel.add(chapterInput);
		
		JLabel lblDernierChapitre = new JLabel("Dernier Chapitre: ");
		lblDernierChapitre.setBounds(154, 102, 148, 28);
		panel.add(lblDernierChapitre);
		
		JLabel lastChapterText = new JLabel("000");
		lastChapterText.setBounds(299, 102, 148, 28);
		panel.add(lastChapterText);
		
		JTextArea progressBarTxt = new JTextArea();
		progressBarTxt.setText("Aucun téléchargement en cours");
		progressBarTxt.setEditable(false);
		progressBarTxt.setBounds(154, 353, 453, 72);
		panel.add(progressBarTxt);
		
		JLabel chapterAvailableText = new JLabel("En attente");
		chapterAvailableText.setFont(new Font("Dialog", Font.BOLD, 14));
		chapterAvailableText.setForeground(new Color(255, 165, 0));
		chapterAvailableText.setBounds(175, 196, 227, 28);
		panel.add(chapterAvailableText);
		
		JButton downloadButton = new JButton("Télécharger");
		downloadButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				progressBarTxt.setText("Téléchargement en cours ...");
			}
		});
		downloadButton.addActionListener(e->{
			String downloadDirPath = scanService.createDownloadDir(m.getName(), chInput);

			for(int i = 1; i <= totalPage; i++) {
				String imgUrl = scanService.getCurrentImgUrl(scanService, i, doc, url);	
				scanService.downloadImg(imgUrl, downloadDirPath, i);
			}
			progressBarTxt.setText("Téléchargement fini, Rendez vous ici:"+ downloadDirPath);
		});

		downloadButton.setEnabled(false);
		downloadButton.setBounds(228, 300, 155, 25);
		panel.add(downloadButton);
		
		Object[] elements = new Object[]{"scantrad.net", "scanvf.net"};
		
		JComboBox<Object> sourceList = new JComboBox<>(elements);
		sourceList.setBounds(154, 264, 293, 24);
		panel.add(sourceList);
		
		
		JLabel lblSatus = new JLabel("Status:");
		lblSatus.setBounds(154, 67, 148, 28);
		panel.add(lblSatus);
		
		JLabel statusMangaTxt = new JLabel("En attente");
		statusMangaTxt.setBounds(299, 67, 148, 28);
		panel.add(statusMangaTxt);
		
		JButton helpButton = new JButton("Voir l'aide");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HelpWindow(mangaService);
			}
		});
		helpButton.setIcon(new ImageIcon("src/main/resources/information.svg"));
		helpButton.setBounds(23, 364, 117, 25);
		panel.add(helpButton);
		
		JButton checkChapterButton = new JButton("Vérifier");
		checkChapterButton.setEnabled(false);
		checkChapterButton.addActionListener(e -> {
			scanService.resetUrls();
			boolean isAvailable = false;
			
			try {
				chInput = Integer.parseInt(chapterInput.getText());
			} catch (Exception e2) {
				chInput = 0;
			}
			url = scanService.getImgUrl(m.getUrl(), chInput);
			try {
				doc = scanService.connect(url);
				totalPage = scanService.getTotalPage(doc);				
			}catch (Exception e3) {
				totalPage = 0;
			}
			isAvailable = (totalPage > 0);
			if(isAvailable) {
				chapterAvailableText.setText("Chapitre Disponible");
				chapterAvailableText.setForeground(Color.GREEN);
			} else {
				chapterAvailableText.setText("Chapitre Indisponible");
				chapterAvailableText.setForeground(Color.RED);
			}
			downloadButton.setEnabled(isAvailable);
			
		});
		checkChapterButton.setBounds(341, 158, 117, 25);
		panel.add(checkChapterButton);
		
		JButton checkMangaButton = new JButton("Vérifier");
		checkMangaButton.addActionListener(e -> {
			
			mangaService.resetMangas();
			setMangaAvailable(sourceList);
			String mangaName = nameInput.getText();
			
			m = mangaService.findByName(mangaName);
			if(m == null) {
				statusMangaTxt.setText("Non trouvé");
				statusMangaTxt.setForeground(Color.RED);
			} else {
				statusMangaTxt.setText("Trouvé");
				statusMangaTxt.setForeground(Color.GREEN);
				lastChapterText.setText(String.valueOf(m.getLastChapter()));
				checkChapterButton.setEnabled(true);
			}	
		});
		checkMangaButton.setBounds(342, 41, 117, 25);
		panel.add(checkMangaButton);
		
		setMangaAvailable(sourceList);
	}
}
