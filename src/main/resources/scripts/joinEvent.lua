local key = KEYS[1]
local limit = tonumber(ARGV[1])
local user = ARGV[2]
local size = redis.call('SCARD', key)
if redis.call('SISMEMBER', key, user) == 1 then
    return "duplicated"
end
if size >= limit then
    return "failed"
else
    redis.call('SADD', key, user)
    return "success"
end