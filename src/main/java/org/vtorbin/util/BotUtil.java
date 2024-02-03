package org.vtorbin.util;

import lombok.Getter;
import org.vtorbin.model.Bot;

public class BotUtil {
    @Getter
    private static final Bot bot = init();

    private static Bot init(){
        return new Bot();
    }
}
