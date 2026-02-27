package app.view;

import app.arbol.ArbolService;
import app.arbol.NodoGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AnalisisArbolForm {
    public JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JLabel LblSubtitulo;
    private JLabel LblValor;
    private JButton btnGenerar;
    private JTextField txtDatos;
    private JButton btnEvaluarCamino;
    private JButton btnAncestros;
    private JButton btnNcestrosPropios;
    private JButton btnRecorridos;
    private JLabel lblConsola;
    private JButton btnDescendientes;
    private JButton btnBuscarRaiz;
    private JButton btnGetCaminos;
    private JTextArea txtConsola;
    private JTree ViewArbol;
    private JButton btnHojas;
    private JButton btnAltura;
    private JButton btnProfundidad;
    private JButton btnHermanoDer;
    private JButton btnHermanoIzq;
    private JButton btnHermanos;
    private JButton btnHijos;
    private JTextField txtNodo;
    private JTextField txtLongitud;
    private JTextField txtSecuencia;

    private ArbolService service;

    //Constructor
    public AnalisisArbolForm() {
        service = new ArbolService();
        deshabilitarBotones();
        txtConsola.setEditable(false);
    }

    private void deshabilitarBotones() {

        btnBuscarRaiz.setEnabled(false);
        btnGetCaminos.setEnabled(false);
        btnEvaluarCamino.setEnabled(false);
        btnAncestros.setEnabled(false);
        btnNcestrosPropios.setEnabled(false);
        btnDescendientes.setEnabled(false);
        btnHojas.setEnabled(false);
        btnAltura.setEnabled(false);
        btnProfundidad.setEnabled(false);
        btnHermanoDer.setEnabled(false);
        btnHermanoIzq.setEnabled(false);
        btnHermanos.setEnabled(false);
        btnHijos.setEnabled(false);
        btnRecorridos.setEnabled(false);
    }

    private void habilitarBotones() {

        btnBuscarRaiz.setEnabled(true);
        btnGetCaminos.setEnabled(true);
        btnEvaluarCamino.setEnabled(true);
        btnAncestros.setEnabled(true);
        btnNcestrosPropios.setEnabled(true);
        btnDescendientes.setEnabled(true);
        btnHojas.setEnabled(true);
        btnAltura.setEnabled(true);
        btnProfundidad.setEnabled(true);
        btnHermanoDer.setEnabled(true);
        btnHermanoIzq.setEnabled(true);
        btnHermanos.setEnabled(true);
        btnHijos.setEnabled(true);
        btnRecorridos.setEnabled(true);
    }

    private void btnGenerarActionPerformed(ActionEvent evt) {

        String datos = txtDatos.getText().trim();

        if (datos.isEmpty()) {
            txtConsola.setText("Debe ingresar los datos del arbol.");
            return;
        }

        String[] valores = datos.split(",");

        if (valores.length < 16) {
            txtConsola.setText("Debe ingresar todos los nodos del arbol.");
            return;
        }

        service.reset();

        // Crear estructura exacta

        service.insertar("A", null);

        service.insertar("B", "A");
        service.insertar("C", "A");
        service.insertar("D", "A");
        service.insertar("E", "A");

        service.insertar("F", "B");
        service.insertar("G", "F");
        service.insertar("H", "G");

        service.insertar("I", "C");
        service.insertar("J", "C");
        service.insertar("K", "J");
        service.insertar("L", "K");
        service.insertar("M", "K");

        service.insertar("N", "E");
        service.insertar("P", "N");
        service.insertar("Q", "N");

        txtConsola.setText("Arbol generado correctamente.");

        btnGenerar.setEnabled(false);
        txtDatos.setText("");
        txtDatos.setEnabled(false);
        actualizarJTree();
        habilitarBotones();
    }
    private void actualizarJTree() {

        if (service.obtenerNodoRaiz() == null) {
            return;
        }

        DefaultMutableTreeNode raizVisual =
                construirNodo(service.obtenerNodoRaiz());

        DefaultTreeModel modelo = new DefaultTreeModel(raizVisual);

        ViewArbol.setModel(modelo);

        for (int i = 0; i < ViewArbol.getRowCount(); i++) {
            ViewArbol.expandRow(i);
        }
    }
    private DefaultMutableTreeNode construirNodo(NodoGeneral<String> nodo) {

        DefaultMutableTreeNode nuevo =
                new DefaultMutableTreeNode(nodo.getDato());

        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            nuevo.add(construirNodo(hijo));
        }

        return nuevo;
    }

    private void btnBuscarRaizActionPerformed(ActionEvent evt) {

        String raiz = service.obtenerRaiz();

        txtConsola.setText("Raíz del árbol: " + raiz);

        // Aquí luego puedes resaltar en JTree
    }

    private void btnGetCaminosActionPerformed(ActionEvent evt) {

        String input = txtLongitud.getText().trim();

        if (input.isEmpty()) {
            txtConsola.setText("Debe ingresar una longitud.");
            return;
        }

        try {
            int longitud = Integer.parseInt(input);

            if (longitud < 0) {
                txtConsola.setText("La longitud debe ser >= 0.");
                return;
            }

            int total = service.contarCaminos(longitud);

            txtConsola.setText("Caminos de longitud " + longitud + ": " + total);

        } catch (NumberFormatException e) {
            txtConsola.setText("Debe ingresar un numero valido.");
        }
    }

    private void btnEvaluarCaminoActionPerformed(ActionEvent evt) {

        String input = txtSecuencia.getText().trim();

        if (input.isEmpty()) {
            txtConsola.setText("Debe ingresar una secuencia.");
            return;
        }

        String[] valores = input.split(",");
        List<String> lista = Arrays.asList(valores);

        boolean esCamino = service.esCamino(lista);

        if (esCamino) {
            txtConsola.setText("La secuencia ES un camino valido.");
        } else {
            txtConsola.setText("La secuencia NO es un camino valido.");
        }
    }

    private void btnAncestrosActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            txtConsola.setText("Debe ingresar un nodo.");
            return;
        }

        if (!service.existeNodo(nodo)) {
            txtConsola.setText("El nodo no existe.");
            return;
        }

        if (nodo.equals(service.obtenerRaiz())) {
            txtConsola.setText("La raiz no tiene ancestros.");
            return;
        }

        txtConsola.setText("Ancestros: " + service.ancestros(nodo));
    }

    private void btnDescendientesActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            txtConsola.setText("Debe ingresar un nodo.");
            return;
        }

        if (!service.existeNodo(nodo)) {
            txtConsola.setText("El nodo no existe.");
            return;
        }

        txtConsola.setText("Descendientes: " + service.descendientes(nodo));
    }

    private void btnHojasActionPerformed(ActionEvent evt) {

        txtConsola.setText("Hojas: " + service.hojas());
    }

    private void btnAlturaActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            txtConsola.setText("Altura del arbol: " + service.alturaArbol());
            return;
        }

        if (!service.existeNodo(nodo)) {
            txtConsola.setText("El nodo no existe.");
            return;
        }

        txtConsola.setText("Altura de " + nodo + ": " + service.alturaNodo(nodo));
    }

    private void btnProfundidadActionPerformed(ActionEvent evt) {

        String nodo = txtNodo.getText().trim();

        if (nodo.isEmpty()) {
            txtConsola.setText("Debe ingresar un nodo.");
            return;
        }

        if (!service.existeNodo(nodo)) {
            txtConsola.setText("El nodo no existe.");
            return;
        }

        txtConsola.setText("Profundidad de " + nodo + ": " + service.profundidadNodo(nodo));
    }

    private void btnRecorridosActionPerformed(ActionEvent evt) {

        String[] opciones = {"Preorden", "Postorden", "Inorden"};

        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione recorrido",
                "Recorridos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == null) return;

        switch (seleccion) {
            case "Preorden":
                txtConsola.setText("Preorden: " + service.preorden());
                break;
            case "Postorden":
                txtConsola.setText("Postorden: " + service.postorden());
                break;
            case "Inorden":
                txtConsola.setText("Inorden: " + service.inorden());
                break;
            default:
                txtConsola.setText("Seleccione recorrido");
                break;
        }
    }


}
