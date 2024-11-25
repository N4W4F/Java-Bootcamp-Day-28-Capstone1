package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        if (merchants.isEmpty())
            return null;

        return merchants;
    }

    public String addMerchant(Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(merchant.getId())) {
                return "already used";
            }
        }
        merchants.add(merchant);
        return "ok";
    }

    public String updateMerchant(String id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.set(i, merchant);
                return "ok";
            }
        }
        return "invalid id";
    }

    public boolean deleteMerchant(String id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}
