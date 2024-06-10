package com.EcommerceApp.JGFE.services.admin.faq;

import com.EcommerceApp.JGFE.dto.FAQDto;

public interface FAQService {
    FAQDto potFAQ(Long productId, FAQDto faqDto);
}
