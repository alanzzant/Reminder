package reminder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.io.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author n1ght_m4re
 */
public class Ventana extends JFrame implements ActionListener {
    JPanel panelSuperior,panelDerecho,panelCentral;
    JButton guardar = new JButton("GUARDAR");
    JButton nuevo = new JButton("NUEVO");
    JButton eliminar = new JButton("ELIMINAR");
    JLabel HORA,FECHA,TITULO;
    JTextField fecha,hora,titulo;
    JTextArea texto = new JTextArea(10,20);
    JScrollPane scroll = new JScrollPane(texto);
    int n;
    
    public Ventana() {
        setTitle("~ REMINDER ~");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes(this.getContentPane());
        pack();
        setSize(800,550);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../img/NotaIcon.png")));
        directorio();
    }
    
    public void iniciarComponentes(Container contenedor) {
        panelSuperior();
        panelDerecho();
        panelCentral();
        
        contenedor.setLayout(new BorderLayout(4,4));
        
        contenedor.add(panelSuperior,BorderLayout.NORTH);
        contenedor.add(panelDerecho,BorderLayout.EAST);
        contenedor.add(panelCentral,BorderLayout.CENTER);
    }
    
    public void panelSuperior() {
        panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3,3,3,3));
        
        JLabel HORA = new JLabel("         Ingresa la hora:");
        HORA.setFont(new Font("Helvetica",Font.BOLD,16));
        hora = new JTextField();
        JLabel FECHA = new JLabel("         Ingresa la fecha:");
        FECHA.setFont(new Font("Helvetica",Font.BOLD,16));
        fecha = new JTextField();
        JLabel TITULO = new JLabel("         Ingresa el titulo:");
        TITULO.setFont(new Font("Helvetica",Font.BOLD,16));
        titulo = new JTextField();
        guardar.addActionListener(this);
        nuevo.addActionListener(this);
        eliminar.addActionListener(this);
        
        panelSuperior.add(guardar);
        panelSuperior.add(TITULO);
        panelSuperior.add(titulo);
        panelSuperior.add(nuevo);
        panelSuperior.add(HORA);
        panelSuperior.add(hora);
        panelSuperior.add(eliminar);
        panelSuperior.add(FECHA);
        panelSuperior.add(fecha);
    }
    
    public void panelDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho,BoxLayout.Y_AXIS));
    }
    
    public void panelCentral() {
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1,1));
        
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        
        panelCentral.add(scroll);
    }
    
    public void directorio() {
        File folder = new File("/home/n1ght_m4re/REMINDER");
        if(!folder.exists()) {
            folder.mkdir();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Notas notas = new Notas(texto.getText(),titulo.getText(),hora.getText(),fecha.getText());
        
        if(e.getSource() == guardar) {
            try {
                notas.crearNota();
            } catch(IOException ex) {
                
            }
        }
        
        try {
            notas.btnPanelDerecho(titulo,texto,panelDerecho);
        } catch (IOException ex) {
            System.out.println("No se ha podido mostrar tus notas.");
        }
        panelCentral.updateUI();
        panelDerecho.updateUI();
        
        if(e.getSource() == nuevo) {
            titulo.setText("");
            hora.setText("");
            fecha.setText("");
            texto.setText("");
        }
        
        if(e.getSource() == eliminar) {
            notas.borrarNota(titulo,texto);
            panelCentral.updateUI();
            panelDerecho.updateUI();
        }
    }
}
