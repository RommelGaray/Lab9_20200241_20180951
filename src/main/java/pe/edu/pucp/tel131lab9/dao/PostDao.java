package pe.edu.pucp.tel131lab9.dao;

import pe.edu.pucp.tel131lab9.bean.Employee;
import pe.edu.pucp.tel131lab9.bean.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDao extends DaoBase{

    public ArrayList<Post> listPosts() {

        ArrayList<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM post left join employees e on e.employee_id = post.employee_id";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Post post = new Post();
                fetchPostData(post, rs);
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    public Post getPost(int id) {

        Post post = null;

        String sql = "SELECT * FROM post p left join employees e on p.employee_id = e.employee_id "+
                "where p.post_id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    post = new Post();
                    fetchPostData(post, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return post;
    }

    public Post savePost(Post post) {

        return post;
    }

    private void fetchPostData(Post post, ResultSet rs) throws SQLException {
        post.setPostId(rs.getInt(1));
        post.setTitle(rs.getString(2));
        post.setContent(rs.getString(3));
        post.setEmployeeId(rs.getInt(4));

        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("e.employee_id"));
        employee.setFirstName(rs.getString("e.first_name"));
        employee.setLastName(rs.getString("e.last_name"));
        post.setEmployee(employee);
    }


    /** Buscador **/

    public ArrayList<Post> buscarPost(String textoBuscar1) {
        ArrayList<Post> lista = new ArrayList<>();


        String sql = "SELECT *\n" +
                "FROM post p\n" +
                "LEFT JOIN employees e ON p.employee_id = e.employee_id\n" +
                "WHERE p.title LIKE ? or p.content LIKE ? or e.first_name LIKE ? or e.last_name LIKE ?";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + textoBuscar1 + "%");
            preparedStatement.setString(2, "%" + textoBuscar1 + "%");
            preparedStatement.setString(3, "%" + textoBuscar1 + "%");
            preparedStatement.setString(4, "%" + textoBuscar1 + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getInt(1));
                    post.setTitle(resultSet.getString(2));
                    post.setContent(resultSet.getString(3));
                    post.setEmployeeId(resultSet.getInt(4));
                    post.setDatetime(resultSet.getTimestamp(5));

                    Employee employee = new Employee();
                    employee.setEmployeeId(resultSet.getInt("e.employee_id"));
                    employee.setFirstName(resultSet.getString("e.first_name"));
                    employee.setLastName(resultSet.getString("e.last_name"));
                    post.setEmployee(employee);
                    lista.add(post);

                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }



    public ArrayList<Post> detallesPost(){

        ArrayList<Post> lista = new ArrayList<>();
        String sql1 = "SELECT p.* ,count(c.comment) FROM post p left join comments c on p.post_id=c.post_id group by p.post_id";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql1)) {

            while(resultSet.next()){
                Post post = new Post();
                post.setPostId(resultSet.getInt(1));
                post.setDatetime(resultSet.getTimestamp(5));



                lista.add(post);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }



    public void nuevoPost(Post post){
        String sql = "INSERT into post (title,content,employee_id,datetime) values (?,?,?,now()) ";

        try(Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,post.getTitle());
            pstmt.setString(2,post.getContent());
            pstmt.setInt(3,post.getEmployeeId());

        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

}
