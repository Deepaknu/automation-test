package util;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;


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
}

