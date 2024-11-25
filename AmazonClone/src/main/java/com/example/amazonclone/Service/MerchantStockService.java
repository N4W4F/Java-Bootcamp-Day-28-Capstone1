package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final MerchantService merchantService;
    private final ProductService productService;

    public ArrayList<MerchantStock> getMerchantStocks() {
        if (merchantStocks.isEmpty())
            return null;

        return merchantStocks;
    }

    public String addMerchantStock(MerchantStock merchantStock) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equals(merchantStock.getId())) {
                return "already used";
            }
        }
        if (merchantService.getMerchants() == null)
            return "null m";
        if (productService.getProducts() == null)
            return "null p";
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantId())) {
                for (Product p : productService.getProducts()) {
                    if (p.getId().equals(merchantStock.getProductId())) {
                        merchantStocks.add(merchantStock);
                        return "ok";
                    }
                } return "invalid product";
            }
        } return "invalid merchant";
    }

    public String updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                for (Merchant m : merchantService.getMerchants()) {
                    if (m.getId().equals(merchantStock.getMerchantId())) {
                        for (Product p : productService.getProducts()) {
                            if (p.getId().equals(merchantStock.getProductId())) {
                                merchantStocks.set(i, merchantStock);
                                return "ok";
                            }
                        } return "invalid product";
                    }
                } return "invalid merchant";
            }
        }
        return "invalid id";
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    // 9- Extra Endpoint 'restockProduct' is used to restock a current MerchantStock by any amount
    public String restockProduct(String merchant_id, String product_id, int amount) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getMerchantId().equals(merchant_id) && ms.getProductId().equals(product_id)) {
                if (amount > 0) {
                    ms.setStock(ms.getStock() + amount);
                    return "ok";
                } return "invalid amount";
            }
        } return "invalid stock";
    }
}
