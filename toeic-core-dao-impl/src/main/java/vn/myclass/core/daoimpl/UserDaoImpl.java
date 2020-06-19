package vn.myclass.core.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.myclass.core.common.utils.HibernateUtil;
import vn.myclass.core.dao.UserDao;
import vn.myclass.core.data.daoimpl.AbstractDao;
import vn.myclass.core.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao<Integer, UserEntity> implements UserDao {
    public Object[] checkLogin(String name, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        boolean isUserExist = false;
        String roleName = null;
        try {
            StringBuilder sql = new StringBuilder("FROM UserEntity u WHERE u.name= :name AND u.password= :password");
            Query query = session.createQuery(sql.toString());
            query.setParameter("name", name);
            query.setParameter("password", password);
            if(query.list().size() > 0){
                isUserExist = true;
                UserEntity userEntity = (UserEntity) query.uniqueResult();
                roleName = userEntity.getRoleEntity().getName();
            }
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new Object[]{isUserExist, roleName};
    }

    public List<UserEntity> findByUsers(List<String> names) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserEntity> userEntities = new ArrayList<UserEntity>();
        try {
            StringBuilder sql = new StringBuilder("FROM UserEntity ue WHERE ue.name IN (:names)");
            Query query = session.createQuery(sql.toString());
            query.setParameterList("names", names);
            userEntities = query.list();
            transaction.commit();
        }  catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userEntities;
    }
}
