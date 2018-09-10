package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.VideoDao;
import br.com.travelmate.model.Video;

public class VideoFacade {

	private VideoDao videoDao;
	
	public Video salvar(Video video) {
		videoDao = new VideoDao();
        try {
            return videoDao.salvar(video);
        } catch (SQLException ex) {
            Logger.getLogger(VideoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Video> listar(String sql){
    	videoDao = new VideoDao();
        try {
            return videoDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VideoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idvideo) {
    	videoDao = new VideoDao();
        try {
        	videoDao.excluir(idvideo);
        } catch (SQLException ex) {
            Logger.getLogger(VideoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
