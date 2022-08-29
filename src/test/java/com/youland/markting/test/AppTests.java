package com.youland.markting.test;
//package com.youland.dashboard;
//
//import com.youland.commons.enums.loan.Option;
//import com.youland.commons.enums.loan.Type;
//import com.youland.dashboard.component.EmailSender;
//import com.youland.dashboard.dto.letter.PreApprovalLetter;
//import com.youland.dashboard.util.PDFUtil;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.condition.EnabledIf;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ByteArrayResource;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import java.io.ByteArrayOutputStream;
//import java.math.BigDecimal;
//import java.time.Instant;
//
///**
// * @author chenning
// */
//@SpringBootTest
//public class AppTests {
//    @Autowired
//    private EmailSender emailSender;
//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//    boolean enable() { return false; }
//
//    @Test
//    @EnabledIf("enable")
//    public void contextLoads() {
//
//    }
//
//    @Test
//    @EnabledIf("enable")
//    void sendPdfEmail() throws Exception {
//        PreApprovalLetter data = PreApprovalLetter.builder()
//                .name("Junie")
//                .expiredDate(Instant.now())
//                .loanType(Type.FeatureType.ADJUSTABLE)
//                .purchasePrice(new BigDecimal("234114.00"))
//                .downPayment(BigDecimal.TEN)
//                .loanAmount(BigDecimal.ONE)
//                .address("230 5th Avenue")
//                .aptNumber("100")
//                .city("New York")
//                .state("NY")
//                .postcode("10001")
//                .propertyUsage(Option.OccupancyOption.PRIMARY_RESIDENCE.name().toLowerCase())
//                .build();;
//        var params = data.toHashMap();
//
//        String htmlContentPDF = templateEngine.process("pdf/pre-approval_letter", new Context(null, params));
//        ByteArrayOutputStream pdfOutputStream = PDFUtil.generatePdfByteStream(htmlContentPDF);
//        ByteArrayResource pdfInputStream = new ByteArrayResource(pdfOutputStream.toByteArray());
//
//        String to = "ning@youland.com";
//        String cc = "mace@youland.com";
//        String subject = "youland.com | Pre-approval letter";
//        String htmlContentEmail = templateEngine.process("email/pre-approval_letter", new Context(null, params));
//        String attachmentName = "pre-approval letter.pdf";
//
//        emailSender.sendMailMessage(to, subject, htmlContentEmail, pdfInputStream, attachmentName, cc);
//    }
//}
