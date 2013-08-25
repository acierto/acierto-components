package com.aciertoteam.common.utils;

import com.aciertoteam.common.stat.DefaultTimer;
import com.aciertoteam.common.stat.Timer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Bogdan Nechyporenko
 */
public class ContractEqualsBuilderTest {

    @Test
    public void equalsComparisonTest() {
        int[] testSets = new int[]{1000, 10000, 100000};
        for (int testSet: testSets) {
            verticalIndent();
            runContractEqualsBuilder(testSet);
            runEqualsBuilder(testSet);
        }
    }

    private void verticalIndent() {
        System.out.println("\n\n");
    }

    private void runContractEqualsBuilder(int num) {
        printHeader("ContractEqualsBuilder", num);
        List<WithContractEqualsBuilder> list = new ArrayList<WithContractEqualsBuilder>();
        Timer timer = new DefaultTimer();
        timer.start(0);
        for (int i = 0; i < num; i++) {
            doEquals(list, new WithContractEqualsBuilder(getRand(), getRand(), getRand(), getRand()));

        }
        timer.end();
    }

    private void runEqualsBuilder(int num) {
        printHeader("EqualsBuilder", num);
        List<WithEqualsBuilder> list = new ArrayList<WithEqualsBuilder>();
        Timer timer = new DefaultTimer();
        timer.start(0);
        for (int i = 0; i < num; i++) {
            doEquals(list, new WithEqualsBuilder(getRand(), getRand(), getRand(), getRand()));

        }
        timer.end();
    }

    private void printHeader(String name, int num) {
        System.out.println(String.format("---Run test using %s (with %s elements)---", name, num));
    }

    private <T> void doEquals(List<T> list, T t) {
        if (!list.isEmpty()) {
            list.get(0).equals(t);
        }
        list.add(t);
    }

    private String getRand() {
        return String.valueOf(new Random().nextInt(1000));
    }

    private class WithContractEqualsBuilder {
        private String a;
        private String b;
        private String c;
        private String d;

        private WithContractEqualsBuilder(String a, String b, String c, String d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public boolean equals(Object obj) {
            return ContractEqualsBuilder.isEquals(this, obj, "a", "b", "c", "d");
        }
    }

    private class WithEqualsBuilder {
        private String a;
        private String b;
        private String c;
        private String d;

        private WithEqualsBuilder(String a, String b, String c, String d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WithEqualsBuilder)) return false;

            WithEqualsBuilder that = (WithEqualsBuilder) o;

            return new EqualsBuilder().append(a, that.a).append(b, that.b).append(c, that.c).append(d, that.d)
                    .isEquals();
        }
    }

}
