// import { check } from 'k6';
// import http from 'k6/http';
//
// export const options = {
//     scenarios: {
//         beemeapp_userservice_getlistmyfollower: {
//             executor: 'constant-arrival-rate',
//             duration: '30s', // total duration
//             preAllocatedVUs: 5000, // to allocate runtime resources
//
//             rate: 100, // number of constant iterations given timeUnit
//             timeUnit: '1s',
//         },
//     },
// };
//
// export default function () {
//     const headers = { 'Content-Type': 'application/json' };
//     const getfollow = http.post('https://userservice.beemeapp.com//bmausers/getmylistfollow',%7Btoken:'toke'%7D, { headers });
//
//     check(getfollow, {
//         'Post status is 200': (r) => res.status === 200,
//         'Post Content-Type header': (r) => res.headers['Content-Type'] === 'application/json',
//     });
// }