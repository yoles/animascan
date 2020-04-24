package fr.lesueur.yohann.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import fr.lesueur.yohann.print.help.Printer;
import fr.lesueur.yohann.services.MangaService;

public class HelpWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public HelpWindow(MangaService mangaService) {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(300, 300, 800, 600);
		setVisible(true);
		setTitle("Menu d'aide" );   
		
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setSize(800, 600);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout());
		
		JLabel display = new JLabel();
		display.setFont(new Font("Dialog", Font.PLAIN, 14));
		panel.setSize(800, 600);
		display.setText(Printer.helpGUI(mangaService.getMangasAvailable()));
		
		
        JScrollPane scrollPane = new JScrollPane(
        		display,                          
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER); 

	}
}
