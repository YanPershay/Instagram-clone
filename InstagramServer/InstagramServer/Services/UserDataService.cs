using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using InstagramServer.IServices;
using InstagramServer.Models;
using Microsoft.EntityFrameworkCore;

namespace InstagramServer.Services
{
    public class UserDataService : IUserDataService
    {
        private readonly InstagramContext _context;

        public UserDataService(InstagramContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<UserData>> GetAll()
        {
            return await _context.UserData.ToListAsync();
        }
        
        public async Task<UserData> Add(UserData userData)
        {
            var addedData = _context.Add(userData);
            await _context.SaveChangesAsync();
            userData.UserId = addedData.Entity.UserId;

            return userData;
        }

        public async Task<UserData> GetByUserId(Guid id)
        {
            return await _context.UserData.Include(nameof(User)).SingleOrDefaultAsync(x => x.UserId == id);
        }

        public async Task Update(UserData userData)
        {
            var userToUpdate = await GetByUserId(userData.UserId);
            userToUpdate.Firstname = userData.Firstname;
            userToUpdate.Lastname = userData.Lastname;
            userToUpdate.Age = userData.Age;
            userToUpdate.Description = userData.Description;
            userToUpdate.City = userData.City;
            _context.Update(userToUpdate);
            await _context.SaveChangesAsync();
        }
    }
}