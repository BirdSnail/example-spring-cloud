package com.birdsnail.example.example;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelExample {

    public static class User {

        public String name;
        public int age = 2;

    }

    public static class Car {

        public String make;
        public String model;
        private Integer year;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }
    }

    /**
     * spel-绑定root object进行评估
     */
    public static void spelRootObjectTest() {
        Car car = new Car();
        car.setMake("Good manufacturer");
        car.setModel("Model 3");
        car.setYear(2014);

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("model");

        EvaluationContext context = new StandardEvaluationContext(car);
        String result = (String) expression.getValue(context);
        System.out.println(result);

        car.setModel("Model 4");

        System.out.println(expression.getValue(car, String.class));
    }

    /**
     * spel-绑定变量进行评估
     */
    public static void spelVariableTest() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("#user.name");
        StandardEvaluationContext context = new StandardEvaluationContext();
        User user = new User();
        user.age = 10;
        user.name = "yanghuaodng1231";
        context.setVariable("user", user);
        context.setVariable("name", "yanghuaodng");
        String message = exp.getValue(context, String.class);
        System.out.println(message);
    }

    public static void spelVariableTest2() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'ips-crm:' +  #name + #user.age"); // name为null时评估返回固定值
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        User user = new User();
        user.age = 10;
        user.name = "pikapijjj";
        context.setVariable("user", user);
        context.setVariable("name", "杨华栋");
        String message = exp.getValue(context, String.class);
        System.out.println(message);
    }

    /**
     * spel-表达式模板
     */
    public static void spelTemplateTest() {
        User user = new User();
        user.age = 10;
        user.name = "yanghuaodng1231";
        ExpressionParser parser = new SpelExpressionParser();
        String result = parser.parseExpression("my name is : #{name}, my age: #{age}",
                new TemplateParserContext()).getValue(user, String.class);
        System.out.println(result);
    }

    /**
     * spel-关系和逻辑运算
     */
    public static void spelLogicalOperators() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("2 +2");
        System.out.println("2+2 = " + expression.getValue());

        expression = parser.parseExpression("1 eq 1");
        System.out.println("1 eq 1 -->" + expression.getValue());

        expression = parser.parseExpression("2 > 1");
        System.out.println("2是否大于1： " + expression.getValue());

        expression = parser.parseExpression("1 gt 2");
        System.out.println("1是否大于2：" + expression.getValue());

        expression = parser.parseExpression("250 > 200 && 200 < 4000");
        System.out.println("[250>200且200<400] -->" + expression.getValue());

        expression = parser.parseExpression("400 > 300 or 150 < 100");
        System.out.println(expression.getValue());

        expression = parser.parseExpression("2 >1 || 5 <=100");
        System.out.println("[2>1或5<=100] -->" + expression.getValue());

        // 三目运算符
        expression = parser.parseExpression("2 > 1 ? 'a' : 'b'");
        System.out.println(expression.getValue());
    }


    public static void main(String[] args) {
//        spelVariableTest();
//        spelTemplateTest();
//        spelVariableTest2();
        spelLogicalOperators();
    }
}
