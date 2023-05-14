package com.c2psi.businessmanagement.services.contracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Utilities {
   Page toPage(List list, Pageable pageable);
}
