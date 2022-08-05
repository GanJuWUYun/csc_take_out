package com.csc.tackout.dto;

import com.csc.tackout.entity.Setmeal;
import com.csc.tackout.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
