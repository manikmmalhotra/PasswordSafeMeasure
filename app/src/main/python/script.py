import requests
import hashlib
import sys

def request_api_data(query_char):
    url = 'https://api.pwnedpasswords.com/range/' + query_char
    res = requests.get(url)
    if res.status_code != 200:
      raise RuntimeError(f'Error fetching: {res.status_code}, check the api and try again')
    return res

def get_password_leak_count(hashes, hash_to_check):
    hashes = (line.split(':') for line in hashes.text.splitlines())
    for h, count in hashes:
        if h == hash_to_check:
            return count
    return  0

def pwned_api_check(password):
    sha1password = hashlib.sha1(password.encode('utf-8')).hexdigest().upper()
    first5_char, tail = sha1password[:5], sha1password[5:]
    response = request_api_data(first5_char)
    #print(first5_char, tail)
    #   print(response)
    return get_password_leak_count(response,tail)
  

def main(args):
   count = pwned_api_check(args)
   if count:
       s = f'{args} was found {count} times...you should probably change your password!'
   else:
       s = f'{args} was NOT found. Carry on!'
   return s

if __name__ == '__main__':
   main(sys.argv[1:])