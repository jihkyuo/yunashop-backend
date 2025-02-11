package yunacare.yunahospital.domain;

public enum AppointmentStatus {
  PENDING, // 예약 대기
  CONFIRMED, // 예약 확정 => 병원 방문 & 진료 완료
  CANCELLED // 예약 취소
}
