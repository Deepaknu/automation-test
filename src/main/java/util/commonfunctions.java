package util;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


import static base.BaseFactory.*;


public class commonfunctions {

        public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
            return new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver instanceof JavascriptExecutor){
                        JavascriptExecutor je = (JavascriptExecutor) driver;
                        je.executeScript("window.alert = function(){};");
                        je.executeScript("window.confirm = function(){return true;};");
                    }
                    return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return (window.angular !== undefined) && " +
                            "(angular.element(document).injector() !== undefined) && " +
                            "(angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
                }
            };
        }

        public static ExpectedCondition<Boolean> pageLoadFinished() {
            return new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver instanceof JavascriptExecutor){
                        JavascriptExecutor je = (JavascriptExecutor) driver;
                        je.executeScript("window.alert = function(){};");
                        je.executeScript("window.confirm = function(){return true;};");
                    }
                    return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
                }
            };
        }


        public static ExpectedCondition<Boolean> isJqueryCallDone() {
            return new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if(!(Boolean)((JavascriptExecutor) driver).executeScript("return jQuery.active==0")){
                        System.out.println("Ajax call is in Progress....");
                    }
                    return (Boolean)((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
                }
            };
        }

    public static void generateAndSendEmail() throws MessagingException, IOException {

        String emailServer = LoadConfigFile.getValue("smtpserver");
        String emailTo = LoadConfigFile.getValue("SendTo");
        String emailFrom = LoadConfigFile.getValue("SendFrom");
        String emailPort = LoadConfigFile.getValue("port");
        String sendEmail = LoadConfigFile.getValue("SendTo");
        String env = LoadConfigFile.getValue("Environment");

        // Recipient's email ID needs to be mentioned.
        String to = emailTo;

        // Sender's email ID needs to be mentioned
        String from = emailFrom;

        // Assuming you are sending email from localhost
        String host = emailServer;

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        boolean status = true;
        String stat  = "PASS";
        try {

            InternetAddress fromAddress = new InternetAddress(from);
            InternetAddress toAddress = new InternetAddress(to);
            String report = reportFileName;





            String bodyTextFailureResult = "";
            String bodyTextFooter =  "For more detailed report please follow the below location.\n\n" + "Result " +
                    "location: " + report + "\n\n\n\n\n" + "Regards,\n" + "Automation Team.\n\n\n\n\n\n";

            StringBuilder failuredescription = new StringBuilder();

            if(FailedNetworkLinks.size() > 0 ){
                failuredescription.append("The Failed URL's, which are due to Error Message as 'Network Error' : \n");
                for(int i=0;i<FailedNetworkLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+ y +" :  " + FailedNetworkLinks.get(i).trim()+ " \n");
                }
                status = false;
            }

            if(FailedUnknownLinks.size() > 0 ){
                failuredescription.append("\n\nThe Failed URL's, which are due to Error Message as 'Unknown Error' :  \n");
                for(int i=0;i<FailedUnknownLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+ y +" :  " + FailedUnknownLinks.get(i).trim()+ " \n");
                }
                status = false;
            }

            if(FailedNomatchLinks.size() > 0 ){
                failuredescription.append("\n\nThe Failed URL's which are failed due to error Message as 'No Match Found' , which is general error :\n");
                for(int i=0;i<FailedNomatchLinks.size();i++){
                    int y = 0;
                    y = i +1 ;
                    failuredescription.append("URL - "+y +" :  " + FailedNomatchLinks.get(i).trim()+ " \n");
                }
                status = false;
            }
            bodyTextFailureResult = failuredescription.toString();

            String bodyText = "Hi All,\n\n\n " + " ' NIT - URL Verification ' Test Automation Execution Completed. The report is attached as file along with this email."
                    + bodyTextFailureResult +  bodyTextFooter;


            // Create an Internet mail msg.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.setRecipient(Message.RecipientType.TO, toAddress);

            if(! status ){
                stat = "FAILED";
            }

            msg.setSubject("Sanity Suite Completed  on - [" + env + "] with Status ["+stat+"]");
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(bodyText);

            // Set the email attachment file
            FileDataSource fileDataSource = new FileDataSource(report);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());

            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);
            Transport.send(msg);


      /*  try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.CC, emailTo);
            message.setRecipients(Message.RecipientType.TO, to);

            // Set Subject: header field
            message.setSubject(" Sanity Suite Completed  on - "+ env );

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            String report = reportFileName;

            // Fill the message
            messageBodyPart.setText("Hi All,\n\n\n " + " ' NIT - URL Verification ' Test Automation Execution Completed. For more detailed report please follow the below location.\n\n" + "Result " +
                    "location: " + report + "\n\n\n\n\n" + "Regards,\n" + "Automation Team.\n\n\n\n\n\n");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = reportFileName;
            DataSource source = new FileDataSource(filename);
            FileDataSource fileDataSource = new FileDataSource(filename);

            messageBodyPart.setDataHandler(new DataHandler(source));

            multipart.addBodyPart(messageBodyPart);
            addAttachment(multipart, filename);
            //message.setContent(fileDataSource.getName(), "text/html");

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);*/


        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}

    private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
        DataSource source = new FileDataSource(filename);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
    }


}

