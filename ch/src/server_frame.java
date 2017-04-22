

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class server_frame
{
   ArrayList clientOutputStreams;
   ArrayList<String> users;
   String listUser[];
   int indexUser=0;
   public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Unexpected error... \n");
            }

       }

       
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    ta_chat.append("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        ta_chat.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                    	/*listUser[indexUser]=data[0];
                    	indexUser++;*/
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                    	//indexUser--;
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                        ta_chat.append("\n Online users : \n");
                        for (String current_user : users)
                        {
                            ta_chat.append(current_user);
                            ta_chat.append("\n");
                        }    
                        
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        if(message.indexOf("/w") > -1){
                        	whisper(message);
                        }
                        else
                        	tellEveryone(message);
                    } 
                    else 
                    {
                        ta_chat.append("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }

    public server_frame() 
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	 ta_chat = new JTextArea();
         b_start = new javax.swing.JButton();
         b_end = new javax.swing.JButton();
         b_users = new javax.swing.JButton();
         b_clear = new javax.swing.JButton();
         lb_name = new javax.swing.JLabel();
         ta_users = new javax.swing.JTextArea();
         
         JFrame frame = new JFrame();
    
         frame.setTitle("Chat - Server's frame");
     
         frame.setVisible(true);
         frame.setLayout(null);
         frame.setSize(500,550);
         GUI = new JPanel();
         GUI.setSize(500,550);
         GUI.setLayout(null);
         GUI.setBackground(Color.WHITE);
         frame.add(GUI);
         ta_chat.setBounds(25,25, 300,300); 
         
         
         jScrollPane1 = new javax.swing.JScrollPane(ta_chat);
         jScrollPane1.setBounds(25,25,300,300);
         


        GUI.add(jScrollPane1);
         
         b_start.setText("START");
         b_start.setSize(100,25);
         b_start.setLocation(25,425);
         GUI.add(b_start);
         b_start.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 b_startActionPerformed(evt);
             }
         });

         b_end.setText("END");
         b_end.setSize(100,25);
         b_end.setLocation(25,375);
         GUI.add(b_end);
         b_end.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 b_endActionPerformed(evt);
             }
         });

         b_users.setText("Online Users");
         b_users.setSize(100,25);
         b_users.setLocation(360,425);
         GUI.add(b_users);
         b_users.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 b_usersActionPerformed(evt);
             }
         });

         b_clear.setText("Clear");
         b_clear.setSize(100,25);
         b_clear.setLocation(360,375);
         GUI.add(b_clear);
         b_clear.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 b_clearActionPerformed(evt);
             }
         });

         lb_name.setText("TechWorld3g");
         lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

         
         
         ta_users.setBounds(350,25, 110,300);
         ta_users.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 0, 0)));
         ta_users.setLayout(null);
         jScrollPane2 = new javax.swing.JScrollPane(ta_users);
         jScrollPane2.setBounds(350,25,110,300);
         GUI.add(jScrollPane2);
    }// </editor-fold>//GEN-END:initComponents

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        try 
        {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        ta_chat.append("Server stopping... \n");
        
        ta_chat.setText("");
    }//GEN-LAST:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ta_chat.append("Server started...\n");
    }//GEN-LAST:event_b_startActionPerformed

  private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_usersActionPerformed
        ta_chat.append("\n Online users : \n");
        for (String current_user : users)
        {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }    
        ta_chat.append("\n Online users : \n");
        for (int i=0;i<=indexUser;i++)
        {
            ta_chat.append(listUser[i]);
            ta_chat.append("\n");
        }    
        
    }//GEN-LAST:event_b_usersActionPerformed

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            
            public void run() {
                new server_frame();
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(2222);
               
                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				
				ta_chat.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error making a connection. \n");
            }
        }
    }
    
    public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.append("Before " + name + " added. \n");
        users.add(name);
       
        ta_chat.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        ta_users.setText("");
        ta_users.append("Online users : \n");
       
        for  (int i=0;i<users.size();i++)
        {
        	System.out.println(users.size());
        	System.out.println(users.get(i));
            ta_users.append(users.get(i)+"(online)");
            ta_users.append("\n");
        }  
        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {	
    	lb_userList = new JLabel[10];
    	
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        ta_chat.append("Lost a connection. \n");
        ta_users.setText("");
        ta_users.append("Online users : \n");
    	for  (int i=0;i<users.size();i++)
        {
        	System.out.println(users.size());
        	System.out.println(users.get(i));
            ta_users.append(users.get(i)+"(online)");
            ta_users.append("\n");
        }  
        
       for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        System.out.println("Lost Conection");
        tellEveryone(done);
    }
    public void printUser (){
    	 ta_chat.append("\n Online users : \n");
         for  (int i=0;i<users.size();i++)
         {
             ta_chat.append(users.get(i));
             ta_chat.append("\n");
         }  
    }
    
    public void whisper(String message) 
    {
    	String destination=message.split(" ")[1];
            try 
            {
                PrintWriter writer = (PrintWriter) clientOutputStreams.get(users.indexOf(destination));
                writer.println(message);
                ta_chat.append("Whispering: " + message + " to "+ destination +"\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
            	ta_chat.append("Error whispering to "+destination+". \n");
            }
    }
    
    public void tellEveryone(String message) 
    {
    	System.out.println(this.clientOutputStreams.size());
    	for(int i=0;i<this.clientOutputStreams.size();i++){
    		//if(this.users.get(i).indexOf(message.split(":")[0])==-1)
			if(message.indexOf(":has connected")>-1)
    			try {
					PrintWriter writer = (PrintWriter) this.clientOutputStreams.get(i);
					writer.println(message);
					ta_chat.append("Sending: " + message + "\n");
					writer.flush();
					ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

				} catch (Exception ex) {
					ta_chat.append("Error telling everyone. \n");
				}
			else if(this.users.size() > 0){
				if(this.users.get(i).indexOf(message.split(":")[0])==-1)
					try {
						PrintWriter writer = (PrintWriter) this.clientOutputStreams.get(i);
						writer.println(message);
						ta_chat.append("Sending: " + message + "\n");
						writer.flush();
						ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

					} catch (Exception ex) {
						ta_chat.append("Error telling everyone. \n");
					}
			}
    	}
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_userList[];
    private javax.swing.JPanel GUI;
    private javax.swing.JTextArea ta_users;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}
