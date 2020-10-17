export function calcTotalSecond(startDateStr, endDateStr) {
  let date1 = new Date(startDateStr);             // 开始时间
  let date2 = new Date(endDateStr);               // 结束时间
  let timeSub = date2.getTime() - date1.getTime();  // 时间差毫秒
  return timeSub / 1000;
}

export function formatTotalDateSub (secondSub) {
  let days = Math.floor(secondSub / (24 * 3600));     // 计算出小时数
  let leave1 = secondSub % (24*3600) ;                // 计算天数后剩余的毫秒数
  let hours = Math.floor(leave1 / 3600);              // 计算相差分钟数
  let leave2 = leave1 % (3600);                       // 计算小时数后剩余的毫秒数
  let minutes = Math.floor(leave2 / 60);              // 计算相差秒数
  let leave3 = leave2 % 60;                           // 计算分钟数后剩余的毫秒数
  let seconds = Math.round(leave3);
  return days + " 天 " + hours + " 时 " + minutes + " 分 " + seconds + ' 秒';
}
