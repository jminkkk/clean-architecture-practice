package jiminkkk.practice.cleanarchitecturepracitce.domain;

// 한 계좌에서 다른 계좌로 송금하는 유스케이스

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

// 입금과 출력을 할 수 있는 Account 엔티티 생성
// 출금 계좌에서 돈을 출금해 입금 계좌로 돈을 입금하는 유스케이스
// 아래 엔티티는 실제 계좌의 현재 스냅샷 제공 -> 계좌에 대한 모든 입금과 출금은 Activity 엔티티에 포착
@Getter
@RequiredArgsConstructor
public class Account {
    private AccountId id; // 계좌 id
    private Money baselineBalance; // 기준 잔액
    private ActivityWindow activityWindow; // 활동창
    // -> 현재 계좌의 전체 계좌 활동 리스트에서 특정 범위의 계쫘 활동만 볼 수 있는 창을 의미

    // 현재 총 잔고를 계산하는 메서드
    // -> 기준 잔고에 활동창의 모든 활동들의 잔고를 합한 값
    public Money calculateBalance() {
        return Money.add(
                this.baselineBalance,
                this.activityWindow.calculateBalance(this.id));
    }

    // 출금
    // calculateBalance() 덕분에 새로운 활동을 활동창에 추가하는 역할만 하게 됨
    public boolean withdraw(Money money, AccountId targetAccoutnId) {
        if (!mayWithdraw(money)) return false;

        Activity withdrawal = new Activity(
                this.id,
                this.id,
                targetAccoutnId,
                LocalDateTime.now(),
                money);

        this.activityWindow.addActivity(withdrawal);
        return true;
    }

    //
    private boolean mayWithdraw(Money money) {
        return Money.add(
                this.calculateBalance(),
                money.negate()).isPositive();
    }

    // 입금
    // calculateBalance() 덕분에 새로운 활동을 활동창에 추가하는 역할만 하게 됨
    public boolean deposit(Money money, AccountId sourceAccountID) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountID,
                this.id,
                LocalDateTime.now(),
                money
        );
        this.activityWindow.addActivity(deposit);
        return true;
    }
}
