package object;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

public class MemberDAO 
{
	private DataSource _source;
	
	public MemberDAO()
	{
		try {
			_source = getDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DataSource getDataSource() throws NamingException 
	{
		Context ctx = new InitialContext();
		Context env = (Context) ctx.lookup("java:/comp/env");
		
		return (DataSource) env.lookup("jdbc/mysql");
	}
	
	public void deleteMember(String userID)
	{
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_MEMBER_D(?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setString(1, userID);
            
            statement.executeUpdate();
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertMember(MemberVO member)
	{
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_MEMBER_I(?, ?, ?, ?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setString(1, member.getId());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());
            statement.setString(4, member.getEmail());
            
            statement.executeUpdate();
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateMember(MemberVO member)
	{
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();

            builder.append("CALL UP_MEMBER_U(?, ?, ?, ?)");
            
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            statement.setString(1, member.getId());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());
            statement.setString(4, member.getEmail());
            
            statement.executeUpdate();
            statement.close();
            connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> getMemberList() 
	{
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();
			
			builder.append("CALL UP_MEMBER_LIST_Q()");
			
			PreparedStatement statement = connection.prepareStatement(builder.toString());
			ResultSet set = statement.executeQuery();
			
			while (set.next())
			{
				MemberVO member = new MemberVO();
				
				member.setId(set.getString("USER_ID"));
				member.setPassword(set.getString("USER_PW"));
				member.setName(set.getString("NAME"));
				member.setEmail(set.getString("MAIL_ADR"));
				member.setJoinDate(set.getDate("JOIN_DATE"));
				
				list.add(member);
			}
			
			set.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public MemberVO getMember(String userID) {
		MemberVO member = new MemberVO();
		
		try {
			Connection connection = _source.getConnection();
			StringBuilder builder = new StringBuilder();
			
			builder.append("CALL UP_MEMBER_Q(?)");
			
			PreparedStatement statement = connection.prepareStatement(builder.toString());
			
			statement.setString(1, userID);
			
			ResultSet set = statement.executeQuery();
			set.next();

			member.setId(set.getString("USER_ID"));
			member.setPassword(set.getString("USER_PW"));
			member.setName(set.getString("NAME"));
			member.setEmail(set.getString("MAIL_ADR"));
			member.setJoinDate(set.getDate("JOIN_DATE"));
			
			set.close();
			statement.close();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return member;
	}
}