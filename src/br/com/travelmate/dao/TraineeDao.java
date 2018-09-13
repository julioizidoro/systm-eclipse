/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controletrainee;
import br.com.travelmate.model.Trainee;

/**
 *
 * @author Wolverine
 */
public class TraineeDao {
    
    public Trainee salvar(Trainee trainee) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        trainee = manager.merge(trainee);
        tx.commit();
        manager.close();
        return trainee;
    }
    
   
    
    public Trainee consultar(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select v from Trainee v where v.vendas.idvendas=" + idVenda);
        Trainee trainee= null;
        if (q.getResultList().size() > 0) {
            trainee = (Trainee) q.getResultList().get(0);
        } 
        manager.close();
        return trainee;
    }
    
    public void excluir(int idTrainee) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Trainee trainee = manager.find(Trainee.class, idTrainee);
        manager.remove(trainee);
        tx.commit();
        manager.close();
    }
    
    public List<Trainee> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        @SuppressWarnings("unchecked")
		List<Trainee> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Controletrainee salvar(Controletrainee controle) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
    public List<Controletrainee> listaControle(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        @SuppressWarnings("unchecked")
		List<Controletrainee> lista = q.getResultList();
        manager.close();
        return lista;
    }
        
    public ResultSet ExportarExcel(String nomeRelatorio, String local, String porta, String senha, String banco, String usuario, String caminhoSalvarExcel, int idUnidade) throws IOException {
        try {
            ResultSet rs;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try {String conexao = "jdbc:mysql://" + local + ":" + porta + "/" + banco;
                com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(conexao, usuario, senha);
                com.mysql.jdbc.Statement stm = (com.mysql.jdbc.Statement) conn.createStatement();

                stm.execute("USE systm"); //Nome do DATABASE A SER ACESSADO  
                String sql = null;
                if (idUnidade==0){
                    sql = "Select * from viewExportarControleTainee";
                }else sql = "Select * from viewExportarControleTrainee where idUnidadeNegocio=" + idUnidade;
                rs = stm.executeQuery(sql);
                //GiroProdutoController giroProdutoController = new GiroProdutoController();
                
                    StringBuffer contenu; //// acho que seria melhor usar o StringBuilder
                    contenu = new StringBuffer("");
                    ResultSetMetaData rsMeta = rs.getMetaData();
                    for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                    contenu.append(rsMeta.getColumnLabel(i) + "\t"); /// nesta linha imprime somente os nome dos campos da tabela  
                }
                contenu.append("\n"); // e temos que colocar todos os dados no StringBuffer  
                rs.beforeFirst();
                while (rs.next()) {
                    for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                        contenu.append(rs.getString(i) + "\t"); /// aqui mostra todos os dados  
                    }
                    contenu.append("\n");

                } //fim do while  
                //agora, salvando o StringBuffer no arquivo  
                FileWriter excelFile = new FileWriter(caminhoSalvarExcel); // nome do arquivo  
                excelFile.write(new String(contenu)); //aqui ele passa a String para salvar  
                excelFile.close();
                JOptionPane.showMessageDialog(null, "Controle Exportado com Sucesso");
                return rs;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public Controletrainee consultarControle(int idVenda) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controletrainee c where c.vendas.idvendas=" + idVenda);
        Controletrainee trainee= null;
        if (q.getResultList().size() > 0) {
            trainee = (Controletrainee) q.getResultList().get(0);
        } 
        manager.close();
        return trainee;
    }
}
